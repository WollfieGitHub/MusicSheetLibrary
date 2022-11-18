package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.MetadataPrompt;
import fr.wollfie.sheetmusiclibrary.components.overlay.OverlayDisplayHandler;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class EditableArtist extends EditableValue<Artist> {
    
    private Node artistNode = new StackPane();
    private boolean doDisplayBtn;

    @SuppressWarnings("unchecked")
    public EditableArtist() {
        // Set icon
        PEN_ICON.setIconColor(ThemeManager.getWhiteColor());
        PEN_ICON.setIconSize(FontSize.DEFAULT_H1);

        // Make appear clickable pen icon to edit
        setOnMouseEntered(event -> { doDisplayBtn = true; reloadDisplay(); setCursor(Cursor.HAND); });
        setOnMouseExited(event -> { doDisplayBtn = false; reloadDisplay(); setCursor(Cursor.DEFAULT); });

        MetadataPrompt<Artist> artistMetadataPrompt = new MetadataPrompt<>("Artist", artist -> {
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
        
        PEN_ICON.setOnMouseClicked(e -> OverlayDisplayHandler.showOverlay(artistMetadataPrompt));

        reloadDisplay();
    }

    
    private void reloadDisplay() {

        getChildren().setAll(artistNode);
        if (doDisplayBtn) { getChildren().add(PEN_ICON); }
        artistNode.requestFocus();
    }
    
    @Override
    protected void onInitializedWith(Artist value) {
        valueProperty.setValue(value);
        reloadDisplay();
    }
}
