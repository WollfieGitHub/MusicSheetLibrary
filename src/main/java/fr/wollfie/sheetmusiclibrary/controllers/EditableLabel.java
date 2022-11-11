package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public class EditableLabel extends HBox {

    private final FontIcon PEN_ICON = new FontIcon(MaterialDesignF.FEATHER);
    
    private final ThemedLabel label;
    private final ThemedTextField textField;
    
    private final StringProperty valueProperty;
    public ReadOnlyStringProperty valuePropertyProperty() { return valueProperty; }

    public EditableLabel(String initialValue, Theme.Category category, int fontSize) {

        label = new ThemedLabel(initialValue, fontSize);
        textField = new ThemedTextField(initialValue, category, fontSize);
        
        label.textProperty().bind(textField.textProperty());
        this.valueProperty = textField.textProperty();
        
        PEN_ICON.setIconColor(ThemeManager.getWhiteColor());
        PEN_ICON.setIconSize(fontSize);

        // Make appear clickable pen icon to edit
        setOnMouseEntered(event -> PEN_ICON.setVisible(true));
        setOnMouseExited(event -> PEN_ICON.setVisible(false));
        
        textField.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, () -> swapTo(label)));
        PEN_ICON.setOnMouseClicked(event -> swapTo(textField));
        
        swapTo(label);
    }
    
    private void swapTo(Node node) { getChildren().setAll(node, PEN_ICON); }
}
