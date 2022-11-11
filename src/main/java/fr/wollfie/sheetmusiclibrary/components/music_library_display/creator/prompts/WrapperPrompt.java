package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.Callback;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public abstract class WrapperPrompt<T, R> extends ValuePrompt<R> {
    
    protected final ValuePrompt<T> innerPrompt;

    /**
     * A component to wrap another prompt of type T, offering additional functionality
     * @param prompt The prompt to display
     * @param callback called when the value is ready
     * @param innerPromptClass The inner prompt type
     */
    public WrapperPrompt(String prompt, Callback<R> callback, Class<? extends ValuePrompt<T>> innerPromptClass) {
        super(prompt, callback);
        ValuePrompt<T> innerPrompt;
        try {
            innerPrompt = innerPromptClass
                    .getConstructor(String.class, Callback.class)
                    .newInstance(prompt, (Callback<T>) this::onInnerCallback);
        } catch (Exception e) { 
            innerPrompt = null;
            Logger.error(e);
        }
        
        this.innerPrompt = innerPrompt;
    }
    
    protected abstract void onInnerCallback(T value);
}
