package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.scene.control.CheckBox;

public class ThemedCheckBox extends CheckBox {
    
    public ThemedCheckBox(Theme.Category category) {
        
        final int size = 40;
        setPrefSize(size, size);
        setMaxSize(size, size);
        setMinSize(size, size);
        
        Logger.infof("Styleclass = %s", getStyleClass());
        /*
        setStyle(
                "-fx-skin: none;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: " + ThemeManager.hexColorFrom(category, Theme.Shade.Default) + ";" +
                "-fx-graphic: none;"
        );
        */
    }
}
