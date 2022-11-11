package fr.wollfie.sheetmusiclibrary.controllers;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SearchResults<T extends MetadataObject> {
    
    protected ListProperty<T> resultProperty = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    private final Class<T> metadataClass;

    public SearchResults(Class<T> metadataClass) {
        this.metadataClass = metadataClass;
    }
    
    public static <T extends MetadataObject> SearchResults<T> createNewFor(Class<T> metadataClass) {
        Preconditions.checkNotNull(metadataClass);
        return new SearchResults<>(metadataClass);
    }

    /**
     * Binds the given string observable such that the results property updates whenever
     * the searchText property does
     * @param searchText The search text property
     */
    public ObservableList<T> boundTo(ObservableStringValue searchText) {

        searchText.addListener((observable, oldValue, newValue) -> this.updateResults(newValue));
        SheetMusicLibrary.getMetadataListProperty(metadataClass).addListener(
                (InvalidationListener)  o -> this.updateResults(searchText.get())
        );
        this.updateResults(searchText.get());
        
        return resultProperty;
    }
    
    @SuppressWarnings("unchecked")
    private void updateResults(String newQuery) {
        this.resultProperty.setAll(
                SheetMusicLibrary.searchFor(
                        newQuery,
                        MetadataType.fromClass(metadataClass)
                ).stream().map(m -> (T)m).toList()
        );
    }
}
