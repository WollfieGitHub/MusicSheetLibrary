package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.function.Consumer;

public class StringPrompt extends ValuePrompt<String> {


    private ThemedTextField textField;

    public StringPrompt(String prompt, Callback<String> callback) {
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

    @Override
    protected BooleanProperty promptDisabledProperty() {
        return textField.disableProperty();
    }
}
