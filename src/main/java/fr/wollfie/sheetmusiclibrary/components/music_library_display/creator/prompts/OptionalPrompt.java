package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedCheckBox;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.util.Optional;

public class OptionalPrompt<T> extends WrapperPrompt<T, Optional<T>> {

    private HBox hBox;
    private ThemedCheckBox checkBox;
    private ThemedButton button;

    /**
     * A component to wrap another prompt of type T, offering additional functionality
     *
     * @param prompt           The prompt to display
     * @param callback         called when the value is ready
     * @param innerPromptClass The inner prompt type
     */
    public OptionalPrompt(String prompt, Callback<Optional<T>> callback, Class<? extends ValuePrompt<T>> innerPromptClass) {
        super(prompt, callback, innerPromptClass);

        innerPrompt.promptDisabledProperty().bind(checkBox.selectedProperty());
        hBox.getChildren().addAll(checkBox, innerPrompt.getNode(), button);
    }
    
    @Override
    protected Node getNode() {
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        checkBox = new ThemedCheckBox(Theme.Category.Accent);
        button = new ThemedButton(null, new FontIcon(MaterialDesignC.CHECK), Theme.Category.Accent);
        
        return hBox;
    }

    @Override
    public void getFocus() {
        checkBox.requestFocus();
    }

    @Override
    protected BooleanProperty promptDisabledProperty() {
        return hBox.disableProperty();
    }

    @Override
    protected void onInnerCallback(T value) {
        this.callback.accept(this.checkBox.isSelected() ? Optional.of(value) : Optional.empty());
    }
}
