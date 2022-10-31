package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.search_results.SearchResults;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public abstract class MetadataItemDisplay<M extends Metadata> extends TableView<M> {

    protected static final int FONT_SIZE = 20;
    
    public MetadataItemDisplay(SearchBar searchBar) {
        setItems(initResults(searchBar));
        
        setPlaceholder(new Label(""));
        getStyleClass().add("noheader");
        setStyle(getStyle() + "-fx-font-size: " + FONT_SIZE + ";");
        
        getColumns().setAll(initColumns());
    }

    protected abstract List<TableColumn<M, ?>> initColumns();

    public MetadataType getContentType() { return MetadataType.fromClass(getContentClass()); }
    
    protected abstract Class<M> getContentClass();
    
    protected ObservableList<M> initResults(SearchBar searchBar) {
        return SearchResults.createNewFor(getContentClass()).boundTo(searchBar.searchTextProperty());
    }
}
