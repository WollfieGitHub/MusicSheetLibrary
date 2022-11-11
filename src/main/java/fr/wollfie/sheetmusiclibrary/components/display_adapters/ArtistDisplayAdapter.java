package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.Card;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ArtistDisplayAdapter extends DisplayAdapter<Artist> {
    
    @Override
    public Node getItemRepresentation(Artist artist) {
        return new Card(
                getArtistPictureComponent(artist),
                getArtistNameComponent(artist)
        );
    }

    @Override
    public MetadataPage<Artist> getPageRepresentation(Artist artist) {
        return null;
    }

    private static Node getArtistPictureComponent(Artist artist) {
        double radius = FontSize.DEFAULT_BIG_ICON;
        // Need to fetch the image when deserialized (which is not the case now since default constructor is used)
        Image image = artist.getImageUrl().available()
                ? artist.getImageUrl().getImage()
                : null;
        Circle circle = new Circle(radius);
        if (image != null) { circle.setFill(new ImagePattern(image)); }
        // Drop a shadow
        circle.setEffect(new DropShadow(10, 0, 0, ThemeManager.colorFrom(
                Theme.Category.Background, Theme.Shade.Dark2
        )));
        return circle;
    }

    private static Node getArtistNameComponent(Artist artist) {
        Label label = new Label(artist.fullName());
        label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";");

        Label datesLabel = new Label(
                artist.getYearOfBirth() + artist.getYearOfDeath().map(y -> " - " + y).orElse("")
        );

        datesLabel.setStyle("-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H3 * 0.7 + ";");

        VBox childBox = new VBox(label, datesLabel);
        childBox.setAlignment(Pos.CENTER_LEFT);
        return childBox;
    }


}
