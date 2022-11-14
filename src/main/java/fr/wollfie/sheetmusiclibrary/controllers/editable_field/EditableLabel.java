package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedLabel;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public class EditableLabel extends EditableValue<String> {

    private final FontIcon PEN_ICON = new FontIcon(MaterialDesignF.FEATHER);
    
    private final ThemedLabel label;
    private final ThemedTextField textField;
    private final boolean updateOnlyOnConfirm;

    public EditableLabel(Theme.Category category, int fontSize, Pos pos) {
        this(category, fontSize, true, pos);
    }
    
    public EditableLabel(Theme.Category category, int fontSize, boolean updateOnlyOnConfirm, Pos pos) {
        setAlignment(pos);
        this.updateOnlyOnConfirm = updateOnlyOnConfirm;
        
        // Instantiate read and write fields
        label = new ThemedLabel(valueProperty().getValue(), fontSize);
        textField = new ThemedTextField(valueProperty().getValue(), category, fontSize);
        
        // Link the properties between each other
        label.textProperty().bind(textField.textProperty());
        // Only update constantly if we asked to
        if (!this.updateOnlyOnConfirm) {
            valueProperty.bindBidirectional(textField.textProperty());
        }
        
        // Set icon
        PEN_ICON.setIconColor(ThemeManager.getWhiteColor());
        PEN_ICON.setIconSize(fontSize);
        PEN_ICON.setVisible(false);

        // Make appear clickable pen icon to edit
        setOnMouseEntered(event -> { PEN_ICON.setVisible(true);  setCursor(Cursor.HAND);    });
        setOnMouseExited(event ->  { PEN_ICON.setVisible(false); setCursor(Cursor.DEFAULT); });
        
        textField.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, () -> swapTo(label)));
        label.setOnMouseClicked(event -> swapTo(textField));
        PEN_ICON.setOnMouseClicked(event -> swapTo(textField));
        
        swapTo(label);
    }
    
    private void swapTo(Node node) { 
               
        getChildren().setAll(node, PEN_ICON);
        node.requestFocus();
        // Select all the text if it's the text field that we are swaping to
        if (node instanceof TextField tf) {
            tf.selectAll(); 
            return;
        }
        
        // If confirm
        if (updateOnlyOnConfirm) {
            this.valueProperty.setValue(textField.getText());
        }
    }

    @Override
    protected void onInitializedWith(String value) {
        this.textField.setText(value);
    }
}
