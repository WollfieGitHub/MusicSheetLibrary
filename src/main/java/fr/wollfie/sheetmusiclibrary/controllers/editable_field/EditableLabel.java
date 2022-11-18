package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedLabel;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public class EditableLabel extends EditableValue<String> {
    
    private final ThemedLabel label;
    private final ThemedTextField textField;

    public EditableLabel(ObjectProperty<UIMode> uiModeProperty, 
            Theme.Category category, int fontSize, Pos pos) {
        this(uiModeProperty, category, fontSize, true, pos);
    }
    
    public EditableLabel(ObjectProperty<UIMode> uiModeProperty,
            Theme.Category category, int fontSize, boolean updateOnlyOnConfirm, Pos pos)
    {
        super(uiModeProperty);
        setAlignment(pos);

        // Instantiate read and write fields
        label = new ThemedLabel(valueProperty().getValue(), fontSize);
        textField = new ThemedTextField(valueProperty().getValue(), category, fontSize);
        
        // Link the properties between each other
        label.textProperty().bind(textField.textProperty());
        // Only update constantly if we asked to
        if (!updateOnlyOnConfirm) {
            valueProperty.bindBidirectional(textField.textProperty());
        }
        
        textField.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, () -> {
            if (updateOnlyOnConfirm) { this.valueProperty.setValue(textField.getText()); }
        }));
    }
    

    @Override
    protected void onInitializedWith(String value) {
        this.textField.setText(value);
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
