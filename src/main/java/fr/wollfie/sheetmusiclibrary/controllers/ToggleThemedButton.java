package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class ToggleThemedButton extends ThemedButton {
    
    public boolean isSelected = false;
    
    /**
     * A button using the theme set in the ThemeManager
     *
     * @param s        : The string to display in the button, can be null
     * @param graphic  : The Icon to display in the button, can be null
     * @param category : The Theme category the button should have
     */
    public ToggleThemedButton(String s, Node graphic, Theme.Category category) {
        super(s, graphic, category);
        this.cornerRadii = 25;
        
        onUpdate();
    }

    @Override
    protected void onUpdate() {
        if (!isSelected) {
            super.onUpdate();
        } else {
            backgroundColor = Color.WHITESMOKE;
            applyStyle();
        }
        
    }

    @Override
    protected void applyStyle() {
        setStyle("-fx-text-fill: " + (!isSelected ? "white" : "black") + ";" +
                "-fx-background-radius: " + cornerRadii + ";" +
                "-fx-background-color: " + Utils.toRGBCode(backgroundColor) + ";" +
                "-fx-font-size: 15;" +
                (hovering ? "-fx-cursor: hand" : ""));
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        onUpdate();
    }
}
