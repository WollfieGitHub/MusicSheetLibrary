package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.components.item_pages.SheetMusicPage;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.dto.Track;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

public class SheetMusicDisplayAdapter extends DisplayAdapter<SheetMusic> {
    
    @Override
    public Node getItemRepresentation(SheetMusic sheetMusic) {
        return new HBox(
                getTitleFrom(sheetMusic),
                getTracksFrom(sheetMusic)
        );
    }

    @Override
    public MetadataPage<SheetMusic> getPageRepresentation(SheetMusic sheetMusic) {
        return new SheetMusicPage(sheetMusic);
    }

    private Node getTracksFrom(SheetMusic sheetMusic) {
        TitledPane root = new TitledPane();
        root.setStyle("-fx-text-fill: " +
                Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker())+ ";" +
                "-fx-font-size: " + (FontSize.DEFAULT_H3*0.8) + ";");
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


        double fontSize = FontSize.DEFAULT_H3;
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
            content.setSpacing(5);
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

            FontIcon instrumentIcon = new FontIcon(track.instrument().getValue().getIcon().getIconCode());
            instrumentIcon.setIconSize((int) fontSize);
            instrumentIcon.setIconColor(ThemeManager.getTextColorFrom(null));

            Label instrumentLbl = new Label(track.instrument().getValue().getName().getEnglishTranslation());
            instrumentLbl.setStyle(style);

            content.getChildren().addAll(instrumentIcon, instrumentLbl);
            content.setSpacing(5);
            return new SimpleObjectProperty<>(content);
        });

        rootContent.getColumns().addAll(clefColumn, instrumentColumn);
        rootContent.setItems(FXCollections.observableList(sheetMusic.getTracks()));

        root.setContent(rootContent);
        return root;
    }

    private Node getTitleFrom(SheetMusic sheetMusic) {
        VBox cell = new VBox();
        Label nameLabel = new ClickableLabel(sheetMusic.getName());
        nameLabel.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";");
        cell.getChildren().addAll(nameLabel);

        if (sheetMusic.isArtistKnown()) {
            Label artistLabel = new ClickableLabel(sheetMusic.getArtistRef().getValue().fullName());
            artistLabel.setStyle("-fx-text-fill: " +
                    Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker())+ ";" +
                    "-fx-font-size: " + FontSize.DEFAULT_H3 + ";");
            cell.getChildren().addAll(artistLabel);
        }

        return cell;
    }

    @Override
    public Pane getListContainer() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        return vBox;
    }
}
