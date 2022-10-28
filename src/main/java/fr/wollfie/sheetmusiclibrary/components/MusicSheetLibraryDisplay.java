package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.utils.BindingUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.Collections;

/**
 * Main panel where the sheet music library is displayed
 */
public class MusicSheetLibraryDisplay extends ScrollPane {

    private final VBox content;
    
    public MusicSheetLibraryDisplay() {
        content = new VBox();
        setContent(content);
        setStyle("-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);");
        
        // Bind the items of the Sheet Music Library to the list of displayed items 
        ObservableList<MusicSheetItemDisplay> items
                = new SimpleListProperty<>(FXCollections.observableList(Collections.emptyList()));

        BindingUtil.mapContent(items, SheetMusicLibrary.sheetMusicsProperty(), MusicSheetItemDisplay::from);

        items.addListener((InvalidationListener) unused -> content.getChildren().setAll(items));
        
    }
}
