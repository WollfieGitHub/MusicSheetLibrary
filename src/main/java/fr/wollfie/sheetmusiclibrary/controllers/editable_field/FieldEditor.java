package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import com.google.common.base.Preconditions;
import javafx.beans.value.ChangeListener;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldEditor {

    private FieldEditor() { }

    /**
     * Synchronize the given editable value's value property with a field for which 
     * you give the setter and getter as argument of this function
     * @param editableValue The control displaying a value which can be edited
     * @param fieldSetter The setter for the field this synchronizer should use
     * @param fieldGetter The getter for the field this synchronizer should use
     * @return A new instance of a synchronizer, which can be used or not
     * @param <T> The type of value that is synchronized
     */
    public static <T> EditableValue<T> synchronize(
            EditableValue<T> editableValue,
            Consumer<T> fieldSetter, Supplier<T> fieldGetter
    ) {
        Preconditions.checkNotNull(editableValue);
        editableValue.initializeWith(fieldGetter.get());
        // Initialize the change listener
        ChangeListener<T> listener = (o, oldV, newV) -> fieldSetter.accept(newV);
        // Initialize the field display and synchronize it with the field
        editableValue.valueProperty().addListener(listener);
        // Make the field name available to subclasses
        return editableValue;
    }
    
    
}
