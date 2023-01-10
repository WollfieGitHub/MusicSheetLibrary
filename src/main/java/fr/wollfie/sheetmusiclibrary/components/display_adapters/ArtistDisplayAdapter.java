package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.components.item_pages.ArtistPage;
import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableCard;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.UIMode;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/** Transforms an artist into its graphic representation */
public final class ArtistDisplayAdapter extends DisplayAdapter<Artist> {
    
    @Override
    public Node getItemRepresentation(Artist artist) {
        ClickableCard card = new ClickableCard(
                getArtistPictureComponent(artist),
                getArtistNameComponent(artist)
        );
        card.setOnMouseClicked(event -> RootComponent.displayPage(artist));
        return card;
    }

    @Override
    public MetadataPage<Artist> getPageRepresentation(Artist artist, ObjectProperty<UIMode> uiModeProperty) {
        return new ArtistPage(artist, uiModeProperty);
    }
    
    /** Generates a picture for the profile of the given artist */
    private static Node getArtistPictureComponent(Artist artist) {
        double radius = FontSize.DEFAULT_BIG_ICON;
        // Need to fetch the image when deserialized (which is not the case now since default constructor is used)
        Image image = artist.getImageUrl().available()
                ? artist.getImageUrl().getImage()
                : null;
        Circle circle = new Circle(radius);
        if (image != null && !image.isError()) { circle.setFill(new ImagePattern(image)); }
        // Drop a shadow
        circle.setEffect(new DropShadow(10, 0, 0, ThemeManager.colorFrom(
                Theme.Category.Background, Theme.Shade.Dark2
        )));
        return circle;
    }

    /** Generates a name label for the artist and its dates (birth & death) */
    private static Node getArtistNameComponent(Artist artist) {
        Label label = new Label(artist.getName());
        label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";");

        Integer yod = artist.getYearOfDeath();
        Label datesLabel = new Label(
                artist.getYearOfBirth() + (yod != null ? " - " + yod : "")
        );

        datesLabel.setStyle("-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H3 * 0.7 + ";");

        VBox childBox = new VBox(label, datesLabel);
        childBox.setAlignment(Pos.CENTER_LEFT);
        return childBox;
    }


}
