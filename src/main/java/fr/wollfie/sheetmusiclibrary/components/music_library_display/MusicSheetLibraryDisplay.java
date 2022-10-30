package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries.*;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;

/**
 * Main panel where the sheet music library is displayed
 */
public class MusicSheetLibraryDisplay extends StackPane {

    private final VBox content;
    private final SearchBar searchBar;
    private final CategorySelectionBar categorySelectionBar;

    public MusicSheetLibraryDisplay() {
        content = new VBox();
        setStyle("-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);");

        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(5, 20, 5, 100));

        searchBar = new SearchBar();

        List<MetadataItemDisplay<?>> libraryDisplays = Arrays.asList(
                new InstrumentLibraryDisplay(searchBar),
                new ArtistLibraryDisplay(searchBar),
                new MusicCategoryLibraryDisplay(searchBar)
        );

        categorySelectionBar = new CategorySelectionBar(libraryDisplays);

        updateContentChildren();
        categorySelectionBar.selectedCategoryProperty().addListener(Utils.onChange(this::updateContentChildren));

        getChildren().add(content);
    }

    private void updateContentChildren() {
        content.getChildren().setAll(searchBar, categorySelectionBar, categorySelectionBar.selectedCategoryProperty().get());
    }

}
