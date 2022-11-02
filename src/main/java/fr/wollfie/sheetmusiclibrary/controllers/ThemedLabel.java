package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ThemedLabel extends Label {

    private final int fontSize;
    private Color backgroundColor;
    
    public ThemedLabel(String text, Color backgroundColor, int fontSize) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.fontSize = fontSize;
        
        init();
    }
    
    public ThemedLabel(String text, int fontSize) {
        this(text, Color.BLACK, fontSize);
    }

    private void init() {
        setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(backgroundColor) + ";" +
                "-fx-font-size: " + fontSize + ";");
    }
}
