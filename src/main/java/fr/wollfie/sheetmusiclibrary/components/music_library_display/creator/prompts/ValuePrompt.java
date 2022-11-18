package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedLabel;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class ValuePrompt<T> extends VBox {
    
    protected Callback<T> callback;
    protected final String prompt;
    
    /**
     * A component to prompt for a value, depending on the type T
     * @param prompt The text to display to prompt to explain what should the input be
     * @param callback The callback function when the result has been retrieved
     */
    public ValuePrompt(String prompt, Callback<T> callback) {
        this.prompt = prompt;
        this.callback = callback;
        
        setAlignment(Pos.CENTER);

        ThemedLabel promptLbl = new ThemedLabel(prompt, 20);
        getChildren().addAll(promptLbl, getContentNode());
    }

    
    
    /** @return the node to mount on the creator component and prompt for a value */
    protected abstract Node getContentNode();

    /** Request the focus for the value prompt */
    public abstract void getFocus();
    
    
    protected abstract BooleanProperty promptDisabledProperty();
    
}
