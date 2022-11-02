package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class TextPrompt extends ValuePrompt<String> {


    private ThemedTextField textField;

    public TextPrompt(String prompt, Consumer<String> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {

        textField = new ThemedTextField("", Theme.Category.Accent, 50);
        textField.setOnKeyPressed(keyEvent -> {if (keyEvent.getCode() == KeyCode.ENTER) {
                callback.accept(textField.getText());
        }});
        textField.setAlignment(Pos.CENTER);
        textField.requestFocus();
        textField.setPrefWidth(150);
        return textField;
    }

    @Override
    public void getFocus() {
        textField.requestFocus();
    }
}
