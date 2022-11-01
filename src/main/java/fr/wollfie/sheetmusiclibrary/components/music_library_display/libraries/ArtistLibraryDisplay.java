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
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");
            return new SimpleObjectProperty<>(label);
        });

        TableColumn<Artist, ImageView> imageTableColumn = new TableColumn<>("");
        imageTableColumn.setCellValueFactory(feature -> {
            Artist artist = feature.getValue();
            // Need to fetch the image when deserialized (which is not the case now since default constructor is used)
            ImageView image = artist.imageUrl().available()
                    ? new ImageView(artist.imageUrl().getImage())
                    : null;
            if (image != null) {
                image.setPreserveRatio(true);
                image.setFitWidth(FONT_SIZE*3);
                image.setStyle("-fx-border-radius: " + 25 + ";");
            }
            
            return new SimpleObjectProperty<>(image);
        });

        return Arrays.asList(imageTableColumn, nameTableColumn);
    }

    @Override
    protected Class<Artist> getContentClass() {
        return Artist.class;
    }
}
