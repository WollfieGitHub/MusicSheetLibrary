package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.MusicGenre;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.http.impl.io.AbstractMessageWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MusicGenreLibraryDisplay extends MetadataItemDisplay<MusicGenre>  {
    
    public MusicGenreLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected List<TableColumn<MusicGenre, ?>> initColumns() {
        TableColumn<MusicGenre, StackPane> nameColumn = new TableColumn<>();
        nameColumn.setCellValueFactory(feature -> {
            MusicGenre musicGenre = feature.getValue();

            Label label = new ClickableLabel(musicGenre.name());

            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");
            StackPane stackPane = new StackPane(label);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            return new SimpleObjectProperty<>(stackPane);
        });

        TableColumn<MusicGenre, StackPane> yearsColumn = new TableColumn<>();
        yearsColumn.setCellValueFactory(feature -> {
            MusicGenre musicGenre = feature.getValue();

            Label label = new Label(
                    musicGenre.yearBegin().map(String::valueOf).orElse("") + " - " +
                    musicGenre.yearEnd().map(String::valueOf).orElse("")
            );

            label.setStyle("-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";" +
                    "-fx-font-size: " + FONT_SIZE * 0.7 + ";");

            StackPane stackPane = new StackPane(label);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            return new SimpleObjectProperty<>(new StackPane(stackPane));
        });
        
        return Arrays.asList(nameColumn, yearsColumn);
    }

    @Override
    protected Class<MusicGenre> getContentClass() {
        return MusicGenre.class;
    }
}
