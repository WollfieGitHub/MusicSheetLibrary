package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.IconChoice;
import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

public class IconPrompt extends ValuePrompt<FontIcon>  {


    private IconChoice iconChoice;

    public IconPrompt(String prompt, Consumer<FontIcon> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {
        iconChoice = new IconChoice(callback, 10, 30);
        return iconChoice;
    }

    @Override
    public void getFocus() {
        iconChoice.getFocus();
    }
}
