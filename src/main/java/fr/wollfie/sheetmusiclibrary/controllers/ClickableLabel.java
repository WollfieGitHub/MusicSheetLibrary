package fr.wollfie.sheetmusiclibrary.controllers;

import javafx.scene.Cursor;
import javafx.scene.control.Label;

public class ClickableLabel extends Label {

    public ClickableLabel(String text) {
        super(text);

        setOnMouseEntered(e -> {
            setUnderline(true);
            setCursor(Cursor.HAND);
        });
        setOnMouseExited(e -> {
            setUnderline(false);
            setCursor(Cursor.DEFAULT);
        });
    }
}
