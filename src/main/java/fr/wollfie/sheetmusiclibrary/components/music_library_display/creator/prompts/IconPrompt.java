package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.IconChoice;
import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

public class IconPrompt extends ValuePrompt<FontIcon>  {


    public IconPrompt(String prompt, Consumer<FontIcon> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {
        return new IconChoice(callback, 10);
    }
}
