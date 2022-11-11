package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

/**
 * Main panel where the sheet music library is displayed
 */
public class LibraryDisplay extends StackPane {

    private final VBox content;
    private final SearchBar searchBar;
    private final CategorySelectionBar categorySelectionBar;
    private final ScrollPane scrollLibraryWrapper;

    private LibraryDisplay() {
        content = new VBox();
        scrollLibraryWrapper = new ScrollPane();
        scrollLibraryWrapper.setStyle(
                "-fx-background-color: transparent;"
        );
        scrollLibraryWrapper.setMaxWidth(Double.MAX_VALUE);
        scrollLibraryWrapper.setFitToWidth(true);
        
        setStyle("-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);");

        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(5, 20, 5, 20));

        searchBar = new SearchBar(15);

        List<MetadataLibraryDisplay<?>> libraryDisplays = Arrays.asList(
                new MetadataLibraryDisplay<>(searchBar, SheetMusic.class),
                new MetadataLibraryDisplay<>(searchBar, Artist.class),
                new MetadataLibraryDisplay<>(searchBar, Instrument.class),
                new MetadataLibraryDisplay<>(searchBar, MusicGenre.class),
                new MetadataLibraryDisplay<>(searchBar, MusicCategory.class)
        );

        categorySelectionBar = new CategorySelectionBar(libraryDisplays);
        categorySelectionBar.setPadding(new Insets(5, 0, 30, 0));

        updateContentChildren();
        categorySelectionBar.selectedCategoryProperty().addListener(Utils.onChange(this::updateContentChildren));
        content.getChildren().setAll(searchBar, categorySelectionBar, scrollLibraryWrapper);
        
        getChildren().add(content);
    }
    
    public static LibraryDisplay initIn(Pane container) {
        LibraryDisplay display = new LibraryDisplay();
        container.getChildren().add(display);
        display.onMounted();
        return display;
    }
    
    private void onMounted() {
        getScene().setOnKeyPressed(Utils.onKeyTyped(KeyCode.TAB, categorySelectionBar::selectNext));
    }

    private void updateContentChildren() {
        scrollLibraryWrapper.setContent(categorySelectionBar.selectedCategoryProperty().get());
    }

}
