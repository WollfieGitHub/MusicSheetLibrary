package fr.wollfie.sheetmusiclibrary.io.metadata;

import fr.wollfie.sheetmusiclibrary.io.FileSystem;
import fr.wollfie.sheetmusiclibrary.io.adapters.MusescoreFileAdapter;
import fr.wollfie.sheetmusiclibrary.io.adapters.PdfFileAdapter;
import fr.wollfie.sheetmusiclibrary.io.adapters.ZipFileAdapter;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.ObservableCompletion;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetadataDropInProgram {
    
    public static EventHandler<DragEvent> handleDragOver(Object dragTarget) {
        return event -> {
            if (event.getGestureSource() != dragTarget && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        };
    }

    public static EventHandler<DragEvent> handleDragDropped() {
        return event -> {
            Dragboard db = event.getDragboard();
            boolean success = db.hasFiles();

            handleDrop(db.getFiles());
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        };
    }

    /**
     * Supported file formats are :
     * <ul>
     *     <li>Portable Document Format files (.pdf)</li>
     *     <li>Musescore files (.mscz)</li>
     * </ul>
     * @param files All files of the supported format that you want to import
     */
    private static void handleDrop(List<File> files) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        for (File file : files) {
            ObservableCompletion observableCompletion = new ObservableCompletion();
            // TODO HANDLE LOADING
            observableCompletion.statusProperty().addListener(Logger::info);
            Platform.runLater(() -> handleDrop(file, observableCompletion));

        }
    }

    private static final Pattern PATTERN = Pattern.compile("^.*\\.(?<extension>pdf|PDF|mscz|MSCZ|mscx|MSCX)$");

    private static void handleDrop(File file, ObservableCompletion observableCompletion) {
        Matcher extensionMatcher = PATTERN.matcher(file.getName());

        // Unsupported format
        if (!extensionMatcher.find()) { observableCompletion.setFailed(); }

        String fileExtension = extensionMatcher.group("extension");

        try {
            switch (fileExtension) {
                case "pdf", "PDF" -> PdfFileAdapter.decodeSheetMusic(file);
                case "mscz", "MSCZ" -> {
                    File unzippedDirectory = ZipFileAdapter.unzipInPlace(file);
                    FileSystem.getFirstFileWith(
                        "mscx",
                        unzippedDirectory
                    ).ifPresent(mscx -> handleDrop(mscx, observableCompletion));
                    if (!FileSystem.deleteDirectory(unzippedDirectory)) {throw new IOException("Deletion failed"); }
                }
                case "mscx", "MSCX" -> observableCompletion.setFromBool(MusescoreFileAdapter.decodeSheetMusic(file));
            }
            observableCompletion.setSuccess();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            observableCompletion.setFailed();
        }
    }
}
