package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.MetadataPrompt;
import fr.wollfie.sheetmusiclibrary.components.overlay.OverlayDisplayHandler;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class EditableArtist extends EditableValue<Artist> {

    private final MetadataPrompt<Artist> artistMetadataPrompt;
    private Node artistNode = new StackPane();

    @SuppressWarnings("unchecked")
    public EditableArtist(ObjectProperty<UIMode> uiModeProperty) {
        super(uiModeProperty);

        artistMetadataPrompt = new MetadataPrompt<>("Artist", artist -> {
            OverlayDisplayHandler.hideOverlay();
            valueProperty.setValue(artist);
        }, Artist.class);

        Binding<Node> artistRepresentation = Bindings.createObjectBinding(() -> {
            Artist artist = valueProperty().getValue();
            // Empty Pane
            if (artist == null) { return null; }
            else { return ((DisplayAdapter<Artist>)artist.getType().displayAdapter).getItemRepresentation(artist); }
        }, valueProperty());
        // actually compute the binding one time before it is used
        valueProperty.setValue(null);
        
        artistRepresentation.addListener((i, oldV, newV) -> {
            this.artistNode = newV == null ? new StackPane() : newV;
            this.reloadDisplay();
        });

        reloadDisplay();
    }

    
    private void reloadDisplay() {
        getChildren().setAll(artistNode);
        
        artistNode.requestFocus();
        artistNode.setOnMouseClicked(event -> {
            switch (getCurrentMode()) {
                case EDIT -> OverlayDisplayHandler.showOverlay(artistMetadataPrompt);
                case READ_ONLY -> RootComponent.displayPage(valueProperty.getValue());
            }
        });
    }
    
    @Override
    protected void onInitializedWith(Artist value) {
        valueProperty.setValue(value);
        reloadDisplay();
    }

    @Override
    protected void onModeChange(UIMode uiMode) {
        
    }
}
