package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import javafx.beans.property.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public abstract class EditableValue<T> extends HBox {
    
    protected final Property<T> valueProperty = new SimpleObjectProperty<>(null);
    public ReadOnlyProperty<T> valueProperty() { return valueProperty; }
    private boolean valueWasInitialized = false;

    private final ReadOnlyObjectProperty<UIMode> uiModeProperty; 
    
    public EditableValue(ObjectProperty<UIMode> uiModeProperty) {
        this.uiModeProperty = uiModeProperty;
        this.uiModeProperty.addListener((i, oldV, newV) -> onModeChange(newV));
        
    }

    /** Initialize with the specified value, cannot be called twice */
    public void initializeWith(T value) {
        if (valueWasInitialized) { throw new UnsupportedOperationException("Value was already initialized to " + valueProperty.getValue()); }

        valueWasInitialized = true;
        this.valueProperty.setValue(value);
        this.onInitializedWith(value);
        this.onModeChange(uiModeProperty.getValue());
    }
    
    protected abstract void onInitializedWith(T value);
    
    protected abstract void onModeChange(UIMode uiMode);
        
    protected UIMode getCurrentMode() {
        return this.uiModeProperty.getValue();
    }
}
