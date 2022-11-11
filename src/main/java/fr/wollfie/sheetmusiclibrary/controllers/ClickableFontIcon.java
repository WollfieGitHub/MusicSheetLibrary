package fr.wollfie.sheetmusiclibrary.controllers;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class ClickableFontIcon extends FontIcon {

    private final Runnable callback;
    private final Color baseColor;
    private final Color variantColor;

    public ClickableFontIcon(Ikon iconCode, Color color, int size, Runnable callback) {
        super(iconCode);
        this.callback = callback;
        this.baseColor = color;
        this.variantColor = color.darker().darker();
        setIconColor(variantColor);
        setIconSize(size);
        init();
    }

    private void init() {
        setOnMouseClicked(event -> callback.run());
        setOnMouseEntered(e -> {
            setUnderline(true);
            setIconColor(this.baseColor);
            setCursor(Cursor.HAND);
        });
        setOnMouseExited(e -> {
            setUnderline(false);
            setIconColor(variantColor);
            setCursor(Cursor.DEFAULT);
        });
    }
}
