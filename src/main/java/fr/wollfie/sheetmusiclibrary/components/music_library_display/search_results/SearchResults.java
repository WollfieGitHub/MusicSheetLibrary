package fr.wollfie.sheetmusiclibrary.components.music_library_display.search_results;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchResults<T extends Metadata> {
    
    protected ListProperty<T> resultProperty = new SimpleListProperty<>();
    
    private final Class<T> metadataClass;

    public SearchResults(Class<T> metadataClass) {
        this.metadataClass = metadataClass;
    }
    
    public static <T extends Metadata> SearchResults<T> createNewFor(Class<T> metadataClass) {
        Preconditions.checkNotNull(metadataClass);
        return new SearchResults<>(metadataClass);
    }

    /**
     * Binds the given string observable such that the results property updates whenever
     * the searchText property does
     * @param searchText The search text property
     */
    public ObservableList<T> boundTo(ObservableStringValue searchText) {
        this.resultProperty.bind(Bindings.createObjectBinding(() -> FXCollections.observableList(
                        SheetMusicLibrary.searchFor(searchText.get(), MetadataType.fromClass(metadataClass))
                                .stream().map(m -> (T)m).toList()
        ), searchText));
        return resultProperty;
    }
}
