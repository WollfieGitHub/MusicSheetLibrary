package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public abstract class ValuePrompt<T> extends VBox {
    
    protected final int fontSize = 10;
    
    // language=CSS-FX
    protected final String baseStyle = "-fx-font-size: " + fontSize + ";" +
            "-fx-font-fill: " + ThemeManager.getTextColorHexFrom(null) + ";";
    
    protected final Consumer<T> callback;
    protected final String prompt;
    
    public ValuePrompt(String prompt, Consumer<T> callback) {
        this.prompt = prompt;
        this.callback = callback;

        Label promptLbl = new Label(prompt);
        getChildren().addAll(promptLbl, getNode());
    }
    
    protected abstract Node getNode();

    public abstract void getFocus();
}
