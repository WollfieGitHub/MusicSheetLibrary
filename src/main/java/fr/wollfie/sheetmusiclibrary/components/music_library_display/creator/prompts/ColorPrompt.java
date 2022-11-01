package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
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
    
    public ColorPrompt(String prompt, Consumer<Color> callback) {
        super(prompt, callback);
    }

    @Override
    protected Node getNode() {
        HBox hBox = new HBox();

        TextFormatter<Color> colorTextFormatter = new TextFormatter<>(new StringConverter<Color>() {
            @Override
            public String toString(Color color) { return Utils.toRGBCode(color); }

            @Override
            public Color fromString(String s) { return Color.web(s); }
        });
        TextField hexColorField = new TextField();
        hexColorField.setTextFormatter(colorTextFormatter);

        ThemedButton button = new ThemedButton("Confirm", null, Theme.Category.Accent);
        
        hBox.getChildren().addAll(hexColorField, button);
        ColorPicker colorPicker = new ColorPicker();
        colorTextFormatter.valueProperty().bindBidirectional(colorPicker.valueProperty());
        
        return new VBox(hBox, colorPicker);
    }
}
