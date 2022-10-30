package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

public class ThemedLabel extends Label {

    private Color backgroundColor;
    
    public ThemedLabel(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        
        init();
    }
    
    public ThemedLabel(String text) {
        this(text, Color.BLACK);
    }

    private void init() {
        setStyle("-fx-text-fill: " + ThemeManager.getTextColorFrom(backgroundColor));
    }
}
