package fr.wollfie.sheetmusiclibrary.utils;

import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.beans.WeakListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class BindingUtil {

    public static <E, F> void mapContent(ObservableList<F> mapped, ObservableList<? extends E> source,
                                         Function<? super E, ? extends F> mapper) {
        map(mapped, source, mapper);
    }

    private static <E, F> Object map(ObservableList<F> mapped, ObservableList<? extends E> source,
                                     Function<? super E, ? extends F> mapper) {
        final ListContentMapping<E, F> contentMapping = new ListContentMapping<>(mapped, mapper);
        mapped.setAll(source.stream().map(mapper).toList());
        source.removeListener(contentMapping);
        source.addListener(contentMapping);
        return contentMapping;
    }

    private static class ListContentMapping<E, F> implements ListChangeListener<E>, WeakListener {
        private final WeakReference<List<F>> mappedRef;
        private final Function<? super E, ? extends F> mapper;

        public ListContentMapping(List<F> mapped, Function<? super E, ? extends F> mapper) {
            this.mappedRef = new WeakReference<>(mapped);
            this.mapper = mapper;
        }

        @Override
        public void onChanged(Change<? extends E> change) {
            final List<F> mapped = mappedRef.get();
            if (mapped == null) {
                change.getList().removeListener(this);
            } else {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        mapped.subList(change.getFrom(), change.getTo()).clear();
                        mapped.addAll(change.getFrom(), change.getList().subList(change.getFrom(), change.getTo())
                                .stream().map(mapper).toList());
                    } else {
                        if (change.wasRemoved()) {
                            mapped.subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                        }
                        if (change.wasAdded()) {
                            mapped.addAll(change.getFrom(), change.getAddedSubList()
                                    .stream().map(mapper).toList());
                        }
                    }
                }
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return mappedRef.get() == null;
        }

        @Override
        public int hashCode() {
            final List<F> list = mappedRef.get();
            return (list == null) ? 0 : list.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final List<F> mapped1 = mappedRef.get();
            if (mapped1 == null) {
                return false;
            }

            if (obj instanceof ListContentMapping) {
                final ListContentMapping<?, ?> other = (ListContentMapping<?, ?>) obj;
                final List<?> mapped2 = other.mappedRef.get();
                return mapped1 == mapped2;
            }
            return false;
        }
    }
    
    public static <T> Pane bindNodeTo(ObjectProperty<T> dependency, Function<T, Node> mapping) {
        StackPane base = new StackPane();
        base.setAlignment(Pos.CENTER);
        
        dependency.addListener((i, oldV, newV) -> {
            base.getChildren().setAll(mapping.apply(newV));
        });
        // Initialization
        base.getChildren().setAll(mapping.apply(dependency.get()));
        return base;
    }
}