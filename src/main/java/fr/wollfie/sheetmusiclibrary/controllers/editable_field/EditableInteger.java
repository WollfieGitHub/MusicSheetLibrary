package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedLabel;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class EditableInteger extends EditableValue<Integer> {

    private final ThemedTextField textField;
    private final ThemedLabel label;
    private TextFormatter<Integer> textFormatter;

    public EditableInteger(ObjectProperty<UIMode> uiModeProperty, Theme.Category category, int fontSize) {
        this(uiModeProperty, category, fontSize, true);
    }

    public EditableInteger(ObjectProperty<UIMode> uiModeProperty, Theme.Category category, int fontSize,
                           boolean updateOnlyOnConfirm) {
        super(uiModeProperty);

        textField = new ThemedTextField(null, category, fontSize);
        textFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                null,
                change -> (change.getControlNewText().matches("-?([1-9][0-9]*)?") ? change : null)
        );
        textField.setTextFormatter(textFormatter);

        textField.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, () -> {
            if (updateOnlyOnConfirm) { this.valueProperty.setValue(textFormatter.getValue()); }
        }));

        if (!updateOnlyOnConfirm) {
            valueProperty.bindBidirectional(textFormatter.valueProperty());
        }

        label = new ThemedLabel("", fontSize);
        label.textProperty().bind(textField.textProperty());
        
    }

    @Override
    protected void onInitializedWith(Integer value) {
        this.textFormatter.setValue(value);
    }

    @Override
    protected void onModeChange(UIMode uiMode) {
        switch (uiMode) {
            case EDIT -> {
                getChildren().setAll(this.textField);
                this.textField.selectAll();
            }
            case READ_ONLY -> {
                getChildren().setAll(this.label);
                this.label.requestFocus();
            }
        }
    }
}
