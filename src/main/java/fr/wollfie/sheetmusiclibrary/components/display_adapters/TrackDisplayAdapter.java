package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.dto.Clef;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.Track;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public final class TrackDisplayAdapter {

    /**
     * Creates a visual representation of the track 
     * @param track The track to display, which we will extract {@link Track#instrument()} and {@link Track#clef()} from
     * @return The visual representation of a track
     */
    public static Node getItemRepresentationGiven(Track track) {
        int fontSize = FontSize.DEFAULT_H2;
        HBox root = new HBox(
                buildClefNode(track.clef(), fontSize),
                buildInstrumentNode(track.instrument().getValue(), fontSize)
        );
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setAlignment(Pos.CENTER_LEFT);
        return root;
    }

    private static Node buildInstrumentNode(Instrument instrument, int fontSize) {
        String style = "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor())+ ";" +
                "-fx-font-size: " + (fontSize) + ";";
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

        FontIcon instrumentIcon = new FontIcon(instrument.getIcon().getIconCode());
        instrumentIcon.setIconSize(fontSize);
        instrumentIcon.setIconColor(ThemeManager.getWhiteColor());

        Label instrumentLbl = new Label(instrument.getName().getEnglishTranslation());
        instrumentLbl.setStyle(style);

        content.getChildren().addAll(instrumentIcon, instrumentLbl);
        content.setSpacing(5);
        return content;
    }

    private static Node buildClefNode(Clef clef, int fontSize) {
        String style = "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor())+ ";" +
                "-fx-font-size: " + (fontSize) + ";" +
                "-fx-border-color: " + Utils.toRGBCode(ThemeManager.getWhiteColor()) + ";" +
                "-fx-border-width: 0 2 0 0;";
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setBackground(new Background(new BackgroundFill(
                ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Light1),
                new CornerRadii(25, 0, 0, 25, false),
                new Insets(0)
        )));
        content.setPadding(new Insets(5));
        content.setSpacing(0);
        content.setStyle(style);

        FontIcon clefIcon = new FontIcon(clef.fontIcon.getIconCode());
        clefIcon.setIconSize(fontSize);
        clefIcon.setIconColor(ThemeManager.getWhiteColor());

        Label clefLbl = new Label(clef.displayName);
        clefLbl.setStyle(
                "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor()) + ";" +
                "-fx-font-size: " + fontSize + ";"
        );

        content.getChildren().addAll(clefIcon, clefLbl);
        content.setSpacing(5);
        return content;
    }
}
