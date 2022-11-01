package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class TextPrompt extends ValuePrompt<String> {


    public TextPrompt(String prompt, Consumer<String> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {

        TextField textField = new TextField();
        textField.setStyle(baseStyle);
        textField.setOnKeyPressed(keyEvent -> {if (keyEvent.getCode() == KeyCode.ENTER) {
                callback.accept(textField.getText());
        }});
        textField.requestFocus();
        return textField;
    }
}
