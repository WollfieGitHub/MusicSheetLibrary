package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.apache.http.impl.io.AbstractMessageWriter;

import java.util.Arrays;
import java.util.List;

public final class ArtistLibraryDisplay extends MetadataItemDisplay<Artist> {
    
    public ArtistLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }
    
    @Override
    protected List<TableColumn<Artist, ?>> initColumns() {
        TableColumn<Artist, VBox> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            Artist artist = feature.getValue();
            Label label = new Label(artist.fullName());
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");
            
            Label datesLabel = new Label(
                    artist.yearOfBirth() + artist.yearOfDeath().map(y -> " - " + y).orElse("")
            );

            datesLabel.setStyle("-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";" +
                    "-fx-font-size: " + FONT_SIZE * 0.7 + ";");
            
            VBox vBox = new VBox(label, datesLabel);
            vBox.setAlignment(Pos.CENTER_LEFT);
            return new SimpleObjectProperty<>(vBox);
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
