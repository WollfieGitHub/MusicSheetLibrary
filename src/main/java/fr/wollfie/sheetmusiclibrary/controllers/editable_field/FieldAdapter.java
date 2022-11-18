package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class FieldAdapter {
    private FieldAdapter() {}
    
    public static <T, R> Consumer<T> adapt(Consumer<R> setter, Function<T, R> mapping) {
        return t -> setter.accept(mapping.apply(t));
    } 
    
    public static <T, R> Supplier<R> adapt(Supplier<T> getter, Function<T, R> mapping) {
        return () -> mapping.apply(getter.get());
    }
}
