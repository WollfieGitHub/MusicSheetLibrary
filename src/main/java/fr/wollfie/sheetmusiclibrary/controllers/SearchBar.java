package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SearchBar extends HBox {

    private final TextField textField;
    private static final int DEFAULT_FONT_SIZE = 15;

    public ReadOnlyStringProperty searchTextProperty() {
        return textField.textProperty();
    }

    public SearchBar() {
        this(DEFAULT_FONT_SIZE);
    }
    
    public SearchBar(int fontSize) {

        textField = new ThemedTextField("", Theme.Category.Accent, fontSize);
        
        FontIcon icon = new FontIcon("mdi2m-magnify");
        icon.setIconColor(ThemeManager.getWhiteColor());
        icon.setIconSize(fontSize);
        
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        
        getChildren().addAll(icon, textField);
    }
    
    public void getFocus() {
        textField.requestFocus();
    }
}
