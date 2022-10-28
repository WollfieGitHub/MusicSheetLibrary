package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.io.MusicLibrary;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.BindingUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

public class MusicSheetLibraryDisplay extends ScrollPane {

    private final VBox content;
    
    public MusicSheetLibraryDisplay() {
        content = new VBox();
        setContent(content);
        setStyle("-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);");

        ObservableList<MusicSheetItemDisplay> items
                = new SimpleListProperty<>(FXCollections.observableList(Collections.emptyList()));

        BindingUtil.mapContent(items, MusicLibrary.sheetMusicsProperty(), MusicSheetItemDisplay::from);

        items.addListener((InvalidationListener) unused -> content.getChildren().setAll(items));
        
    }
}
