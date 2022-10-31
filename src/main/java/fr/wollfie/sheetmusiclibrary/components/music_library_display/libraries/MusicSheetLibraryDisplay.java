package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class MusicSheetLibraryDisplay extends MetadataItemDisplay<SheetMusic> {

    public MusicSheetLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected List<TableColumn<SheetMusic, ?>> initColumns() {
        TableColumn<SheetMusic, VBox> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            SheetMusic sheetMusic = feature.getValue();
            VBox cell = new VBox();
            Label nameLabel = new Label(sheetMusic.name());
            nameLabel.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + (FONT_SIZE) + ";");

            Label artistLabel = new Label(sheetMusic.artistRef().getValue().fullName());
            artistLabel.setStyle("-fx-text-fill: " +
                    Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker())+ ";" +
                    "-fx-font-size: " + (FONT_SIZE*0.7) + ";");

            cell.getChildren().addAll(nameLabel, artistLabel);
            return new SimpleObjectProperty<>(cell);
        });
        return Arrays.asList(nameTableColumn);
    }

    @Override
    protected Class<SheetMusic> getContentClass() {
        return SheetMusic.class;
    }
}
