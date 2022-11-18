package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.util.function.Consumer;

public class ColorPrompt extends ValuePrompt<Color>  {

    private TextField hexColorField;

    public ColorPrompt(String prompt, Callback<Color> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getContentNode() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        TextFormatter<Color> colorTextFormatter = new TextFormatter<>(new StringConverter<>() {
            @Override
            public String toString(Color color) { return color == null ? "" : Utils.toRGBCode(color); }

            @Override
            public Color fromString(String s) { return Color.web(s); }
        });
        hexColorField = new TextField();
        hexColorField.setTextFormatter(colorTextFormatter);

        ThemedButton button = new ThemedButton("Confirm", null, Theme.Category.Accent);
        button.setOnAction(clickEvent -> {
            callback.accept(colorTextFormatter.getValue());
        });
        
        ColorPicker colorPicker = new ColorPicker();
        colorTextFormatter.valueProperty().bindBidirectional(colorPicker.valueProperty());
        hBox.getChildren().addAll(hexColorField, colorPicker);
        
        VBox vBox = new VBox(hBox, button);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    @Override
    public void getFocus() {
        hexColorField.requestFocus();
    }

    @Override
    protected BooleanProperty promptDisabledProperty() {
        return hexColorField.disableProperty();
    }


}
