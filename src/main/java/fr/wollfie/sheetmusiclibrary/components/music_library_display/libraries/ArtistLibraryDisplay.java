package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");
            return new SimpleObjectProperty<>(label);
        });

        TableColumn<Artist, ImagePattern> imageTableColumn = new TableColumn<>("");
        imageTableColumn.setCellValueFactory(feature -> {
            Artist artist = feature.getValue();
            Circle circle = new Circle(FONT_SIZE*1.5);
            // Need to fetch the image when deserialized (which is not the case now since default constructor is used)
            ImagePattern pattern = artist.imageUrl().available()
                    ? new ImagePattern(artist.imageUrl().getImage())
                    : null;
            circle.setFill(pattern);
            
            
            return new SimpleObjectProperty<>(pattern);
        });

        return Arrays.asList(imageTableColumn, nameTableColumn);
    }

    @Override
    protected Class<Artist> getContentClass() {
        return Artist.class;
    }
}
