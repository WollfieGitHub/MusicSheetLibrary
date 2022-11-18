package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.Consumer;

public class IntegerPrompt extends ValuePrompt<Integer> {
    
    private TextField textField;
    
    /**
     * A component to prompt for a value, depending on the type T
     *
     * @param prompt      The text to display to prompt to explain what should the input be
     * @param callback    The callback function when the result has been retrieved
     */
    public IntegerPrompt(String prompt, Callback<Integer> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getContentNode() {
        textField = new ThemedTextField("", Theme.Category.Accent, 50);
        
        TextFormatter<Integer> doubleTextFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                0,
                change -> (change.getControlNewText().matches("-?([1-9][0-9]*)?") ? change : null)
        );

        textField.setOnKeyPressed(keyEvent -> {if (keyEvent.getCode() == KeyCode.ENTER) {
            callback.accept(doubleTextFormatter.getValue());
        }});
        
        textField.setTextFormatter(doubleTextFormatter);
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
