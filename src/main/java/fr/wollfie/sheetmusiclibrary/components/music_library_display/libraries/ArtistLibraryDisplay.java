package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ArtistLibraryDisplay extends MetadataItemDisplay<Artist> {
    
    public ArtistLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected List<TableColumn<Artist, ?>> initColumns() {
        TableColumn<Artist, Label> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            Artist artist = feature.getValue();
            Label label = new Label(artist.fullName());
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorFrom(null));
            return new SimpleObjectProperty<>(label);
        });

        return Collections.singletonList(nameTableColumn);
    }

    @Override
    protected Class<Artist> getContentClass() {
        return Artist.class;
    }
}
