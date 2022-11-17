package fr.wollfie.sheetmusiclibrary.controllers.editable_field;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.MetadataPrompt;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class EditableArtist extends EditableValue<Artist> {
    
    private Node artistNode;
    private boolean doDisplayBtn;

    @SuppressWarnings("unchecked")
    public EditableArtist() {
        // Set icon
        PEN_ICON.setIconColor(ThemeManager.getWhiteColor());
        PEN_ICON.setIconSize(FontSize.DEFAULT_H1);
        PEN_ICON.setVisible(false);

        // Make appear clickable pen icon to edit
        setOnMouseEntered(event -> { doDisplayBtn = true; reloadDisplay(); setCursor(Cursor.HAND); });
        setOnMouseExited(event -> { doDisplayBtn = false; reloadDisplay(); setCursor(Cursor.DEFAULT); });

        MetadataPrompt<Artist> artistMetadataPrompt = new MetadataPrompt<>("Artist", valueProperty::setValue, Artist.class);

        Binding<Node> artistRepresentation = Bindings.createObjectBinding(() -> 
            ((DisplayAdapter<Artist>)valueProperty().getValue().getType().displayAdapter)
                    .getItemRepresentation(valueProperty().getValue()),
                valueProperty());
        
        artistRepresentation.addListener((i, oldV, newV) -> {
            this.artistNode = artistRepresentation.getValue();
            this.reloadDisplay();
        });


        textField.setOnKeyPressed(Utils.onKeyTyped(KeyCode.ENTER, () -> swapTo(label)));
        label.setOnMouseClicked(event -> swapTo(textField));
        PEN_ICON.setOnMouseClicked(event -> swapTo(textField));

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
