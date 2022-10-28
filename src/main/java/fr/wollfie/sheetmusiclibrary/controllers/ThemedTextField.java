package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.scene.control.TextField;

public class ThemedTextField extends TextField {

    private final Theme.Category category;

    /**
     * A text field using the theme set in the ThemeManager
     * @param text The text to display in the text field at initialization
     * @param category The category of the theme the textfield is displayed with
     */
    public ThemedTextField(String text, Theme.Category category) {
        super(text);
        this.category = category;
        
        init();
    }

    private void init() {
        setStyle(
                "-fx-border-width: 0 0 2 0;" +
                "-fx-border-color: "+ ThemeManager.hexColorFrom(category, Theme.Shade.Default) + ";" +
                "-fx-control-inner-background: transparent;" +
                "-fx-background-color: linear-gradient(to top, rgba(0, 0, 0, 0.4), transparent 20%);");
    }
}
