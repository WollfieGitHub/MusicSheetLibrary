package fr.wollfie.sheetmusiclibrary.io.adapters;

import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.exceptions.PdfFormatException;
import fr.wollfie.sheetmusiclibrary.dto.files.PdfFile;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public final class PdfFileAdapter {
    private PdfFileAdapter() {}
    
    /**
     * Converts a PDF file to a json with information about :
     * <ul>
     *      <li>The title of the piece</li>
     *      <li>The author of the piece</li>
     *      <li>The tracks</li>
     * </ul>
     * @param pdfFile The pdf file to conver
     * @throws IOException If the file is not a valid PDF document
     * @throws PdfFormatException if the file is not a valid sheet music
     */
    public static void decodeSheetMusic(File pdfFile) throws IOException {
        try( PDDocument pdf = PDDocument.load(pdfFile) ) {
            if (pdf.isEncrypted()) { throw new IllegalArgumentException("Unable to read encrypted document !"); }
            
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String sheetMusicTitle = Utils.sanitized(stripper.getText(pdf));
                    

            SheetMusic sheetMusic = new SheetMusic(sheetMusicTitle, new PdfFile(pdfFile));
            SheetMusicLibrary.tryInsert(sheetMusic);
        }
    }
    
    public static void openPdfFile(File pdfFile) {
        ProcessBuilder processBuilder = new ProcessBuilder(pdfFile.getAbsolutePath());
        try {
            processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
