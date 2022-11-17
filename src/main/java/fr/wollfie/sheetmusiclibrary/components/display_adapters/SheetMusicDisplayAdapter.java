package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.RootComponent;
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

public final class SheetMusicDisplayAdapter extends DisplayAdapter<SheetMusic> {
    
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
        
        VBox tracksVBox = new VBox();
        tracksVBox.setSpacing(5);
        tracksVBox.setAlignment(Pos.CENTER_LEFT);
        tracksVBox.getChildren().addAll(
                sheetMusic.getTracks()
                        .stream()
                        .map(TrackDisplayAdapter::getItemRepresentationGiven)
                        .toList()
        );
        
        root.setContent(tracksVBox);
        return root;
    }

    private Node getTitleFrom(SheetMusic sheetMusic) {
        VBox cell = new VBox();
        Label nameLabel = new ClickableLabel(sheetMusic.getName());
        nameLabel.setOnMouseClicked(event -> RootComponent.displayPage(sheetMusic));
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
