package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

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

        TableColumn<SheetMusic, TitledPane> instrumentsColumn = new TableColumn<>("");
        instrumentsColumn.setCellValueFactory(feature -> {
            SheetMusic sheetMusic = feature.getValue();
            TitledPane root = new TitledPane();
            root.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";");
            root.setText("Instruments");
            root.setExpanded(false);
            
            final int cellSize = 50;
            
            TableView<Track> rootContent = new TableView<>();
            rootContent.setPadding(new Insets(0));
            rootContent.getStyleClass().add("noheader");
            rootContent.setStyle("-fx-border-width: 0 0 0 1");
            rootContent.setPlaceholder(new Label());
            rootContent.setFixedCellSize(cellSize);
            IntegerBinding integerBinding = Bindings.createIntegerBinding(
                    () ->  rootContent.getItems().size()
                    , rootContent.itemsProperty()
            );
            rootContent.prefHeightProperty().bind(integerBinding.multiply(cellSize).multiply(1.1));


            double fontSize = FONT_SIZE*0.85;
            String style = "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null))+ ";" +
                    "-fx-font-size: " + (fontSize) + ";";
            
            
            TableColumn<Track, HBox> clefColumn = new TableColumn<>();
            clefColumn.setCellValueFactory(trackFeature -> {
                Track track = trackFeature.getValue();
                HBox content = new HBox();
                content.setAlignment(Pos.CENTER_LEFT);
                content.setBackground(new Background(new BackgroundFill(
                        ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Light1),
                        new CornerRadii(0, 0, 0, 0, false),
                        new Insets(0)
                )));
                content.setPadding(new Insets(5));
                content.setSpacing(0);
                content.setStyle(style);

                FontIcon clefIcon = new FontIcon(track.clef().fontIcon.getIconCode());
                clefIcon.setIconSize((int) fontSize);
                clefIcon.setIconColor(ThemeManager.getTextColorFrom(null));

                Label clefLbl = new Label(track.clef().displayName);
                clefLbl.setStyle(style);
                
                content.getChildren().addAll(clefIcon, clefLbl);
                return new SimpleObjectProperty<>(content);
            });

            TableColumn<Track, HBox> instrumentColumn = new TableColumn<>();
            instrumentColumn.setCellValueFactory(trackFeature -> {
                Track track = trackFeature.getValue();
                HBox content = new HBox();
                content.setAlignment(Pos.CENTER_LEFT);
                content.setBackground(new Background(new BackgroundFill(
                        ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Light1),
                        new CornerRadii(0, 25, 25, 0, false),
                        new Insets(0)
                )));
                content.setPadding(new Insets(5));
                content.setSpacing(0);
                content.setStyle(style);

                FontIcon instrumentIcon = new FontIcon(track.instrument().getValue().icon().getIconCode());
                instrumentIcon.setIconSize((int) fontSize);
                instrumentIcon.setIconColor(ThemeManager.getTextColorFrom(null));

                Label instrumentLbl = new Label(track.instrument().getValue().name());
                instrumentLbl.setStyle(style);

                content.getChildren().addAll(instrumentIcon, instrumentLbl);
                return new SimpleObjectProperty<>(content);
            });
            
            rootContent.getColumns().addAll(clefColumn, instrumentColumn);
            rootContent.setItems(FXCollections.observableList(sheetMusic.tracks()));
            
            root.setContent(rootContent);
            return new SimpleObjectProperty<>(root);
        });
        
        return Arrays.asList(nameTableColumn, instrumentsColumn);
    }

    @Override
    protected Class<SheetMusic> getContentClass() {
        return SheetMusic.class;
    }
}
