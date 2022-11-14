package fr.wollfie.sheetmusiclibrary.controllers;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;

public class ClickableCard extends Card {
    
    private boolean mouseHovering;
    
    public ClickableCard(Node content, Node label) {
        super(content, label);
        
        setOnMouseEntered(e -> {
            mouseHovering = true;
            setEffect(new ColorAdjust(0, 0.05, 0.1 , 0));
            setCursor(Cursor.HAND);
        });
        
        setOnMousePressed(event -> setEffect(new ColorAdjust(0, 0.1, 0.15, 0)));
        
        setOnMouseReleased(event -> {
            if (mouseHovering) {
                setEffect(new ColorAdjust(0, 0.05, 0.1 , 0));
            }
        });
        
        setOnMouseExited(e -> {
            mouseHovering = false;
            setEffect(null);
            setCursor(Cursor.DEFAULT);
        });
    }
}
