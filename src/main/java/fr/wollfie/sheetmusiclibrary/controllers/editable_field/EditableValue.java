package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public abstract class EditableValue<T> extends HBox {

    protected final FontIcon PEN_ICON = new FontIcon(MaterialDesignF.FEATHER);
    
    protected final Property<T> valueProperty = new SimpleObjectProperty<>(null);
    public ReadOnlyProperty<T> valueProperty() { return valueProperty; }
    private boolean valueWasInitialized = false;
    
    /** Initialize with the specified value, cannot be called twice */
    public void initializeWith(T value) {
        if (valueWasInitialized) { throw new UnsupportedOperationException("Value was already initialized to " + valueProperty.getValue()); }
        
        this.valueProperty.setValue(value);
        valueWasInitialized = true;
        this.onInitializedWith(value);
    }
    
    protected abstract void onInitializedWith(T value);
        
}
