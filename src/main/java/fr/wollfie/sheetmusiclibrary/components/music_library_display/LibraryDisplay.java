package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.components.CenterSceneContent;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.MetadataCreator;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Main panel where the sheet music library is displayed
 */
public class LibraryDisplay extends StackPane implements CenterSceneContent {

    private final VBox content;
    private final SearchBar searchBar;
    private final CategorySelectionBar categorySelectionBar;
    private final LibraryWrapper libraryWrapper;

    private LibraryDisplay() {
        content = new VBox();

        libraryWrapper = new LibraryWrapper();
        
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
        content.getChildren().setAll(searchBar, categorySelectionBar, libraryWrapper);
        
        getChildren().add(content);
    }
    
    public static LibraryDisplay createNew() {
        return new LibraryDisplay();
    }
    

    private void updateContentChildren() {
        libraryWrapper.setContent(categorySelectionBar.selectedCategoryProperty().get());
    }

    @Override
    public Collection<Node> getContextControls() {
        return Arrays.asList(
                new ClickableFontIcon(MaterialDesignP.PLUS_CIRCLE_OUTLINE,
                        ThemeManager.getWhiteColor(), FontSize.DEFAULT_MEDIUM_ICON,
                        () -> MetadataCreator.promptCreationFor(CategorySelectionBar.currentSelectedType)),
                new ClickableFontIcon(MaterialDesignA.APPLICATION_IMPORT,
                        ThemeManager.getWhiteColor(), FontSize.DEFAULT_MEDIUM_ICON,
                        () -> {})
        );
    }
}
