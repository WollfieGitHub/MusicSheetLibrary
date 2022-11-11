package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.MetadataLibraryDisplay;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class MetadataPrompt<M extends MetadataObject> extends ValuePrompt<M> {

    private SearchBar searchBar;
    private VBox vBox;

    /**
     * A component to prompt for a value, depending on the type T
     *
     * @param prompt   The text to display to prompt to explain what should the input be
     * @param callback The callback function when the result has been retrieved
     * @param mClass   The class of metadata
     */
    public MetadataPrompt(String prompt, Callback<M> callback, Class<M> mClass) {
        super(prompt, callback);

        MetadataLibraryDisplay<M> libraryDisplay = new MetadataLibraryDisplay<>(searchBar, mClass, Utils.onlyOnNonNull(callback));
        vBox.getChildren().addAll(searchBar, libraryDisplay);
        searchBar.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, libraryDisplay::selectFirst));
    }

    @Override
    protected Node getNode() {
        vBox = new VBox();
        searchBar = new SearchBar();
        return vBox;
    }

    @Override
    public void getFocus() {
        searchBar.getFocus();
    }

    @Override
    protected BooleanProperty promptDisabledProperty() {
        return searchBar.disableProperty();
    }
}
