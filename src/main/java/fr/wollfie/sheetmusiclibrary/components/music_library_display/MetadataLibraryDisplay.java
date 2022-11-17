package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.controllers.SearchResults;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.BindingUtil;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MetadataLibraryDisplay<M extends MetadataObject> extends StackPane {

    protected static final int FONT_SIZE = 30;
    private final Class<M> type;
    private final Callback<M> onClick;
    private ObservableList<M> results;

    public MetadataLibraryDisplay(SearchBar searchBar, Class<M> metadataType) {
        this(searchBar, metadataType, null);
    }

    public MetadataLibraryDisplay(SearchBar searchBar, Class<M> metadataType, Callback<M> onClick) {
        this.type = metadataType;
        this.onClick = onClick;
        this.results = initResults(searchBar);
        init(onClick);
    }

    public MetadataLibraryDisplay(ObservableList<M> results, Class<M> metadataType) {
        this(results, metadataType, null);
    }
    
    public MetadataLibraryDisplay(ObservableList<M> results, Class<M> metadataType, Callback<M> onClick) {
        this.type = metadataType;
        this.onClick = onClick;
        this.results = results;

        init(onClick);
    }

    @SuppressWarnings("unchecked")
    private void init(Callback<M> onClick) {
        setMaxWidth(Double.MAX_VALUE);

        Pane listContainer = MetadataType.fromClass(this.type).displayAdapter.getListContainer();

        BindingUtil.mapContent(listContainer.getChildren(), this.results, metadata -> {
            Node node = ((DisplayAdapter<M>)metadata.getType().getAdapter()).getItemRepresentation(metadata);
            
            if (this.onClick != null) { node.setOnMouseClicked(clickEvent -> onClick.accept(metadata)); }
            return node;
        });

        listContainer.setMaxWidth(Double.MAX_VALUE);

        getStyleClass().add("noheader");
        setStyle(getStyle() +
                "-fx-font-size: " + FONT_SIZE + ";" +
                "-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);"
        );
        getChildren().setAll(listContainer);

    }

    public void selectFirst() {
        if (onClick == null) { throw new UnsupportedOperationException("No callback configured on selection"); } 

        onClick.accept(this.results.size() > 0 ? this.results.get(0) : null);
    }

    public MetadataType getContentType() { return MetadataType.fromClass(getContentClass()); }
    
    protected Class<M> getContentClass() { return type; }
    
    protected ObservableList<M> initResults(SearchBar searchBar) {
        return SearchResults.createNewFor(getContentClass()).boundTo(searchBar.searchTextProperty());
    }
}
