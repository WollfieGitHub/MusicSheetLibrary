package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.IconChoice;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

public class FontIconPrompt extends ValuePrompt<FontIcon>  {


    private IconChoice iconChoice;

    public FontIconPrompt(String prompt, Callback<FontIcon> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {
        iconChoice = new IconChoice(callback, 30, 30);
        return iconChoice;
    }

    @Override
    public void getFocus() {
        iconChoice.getFocus();
    }

    @Override
    protected BooleanProperty promptDisabledProperty() {
        return iconChoice.disableProperty();
    }
}
