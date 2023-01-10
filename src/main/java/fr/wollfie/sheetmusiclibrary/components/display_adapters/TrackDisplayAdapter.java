package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.dto.Clef;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.Track;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Curve;
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
import javafx.scene.paint.Color;
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
                buildInstrumentNode(track.instrument().getValue(), fontSize),
                buildClefNode(track.clef(), fontSize)
        );
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setAlignment(Pos.CENTER_LEFT);
        
        Color instrumentColor = track.instrument().getValue().getColor();
        root.setStyle(
                "-fx-border-radius: 25;" +
                "-fx-background-radius: 25;" +
                "-fx-background-color: " + Utils.generateGradient(
                        Utils.BackgroundDirection.TO_RIGHT,
                        instrumentColor.deriveColor(0, 0.7, 0.4, 1),
                        ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Light1),
                        Curve.EXPONENTIAL, 50, 0.8f) + ";" +
                "-fx-border-color: " + Utils.toRGBCode(
                        instrumentColor.deriveColor(0, 0.4, 0.6, 1)
                ) + ";" +
                "-fx-border-width: 1 1 1 1;"
        );
        
        return root;
    }

    private static Node buildInstrumentNode(Instrument instrument, int fontSize) {
        String style = "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor())+ ";" +
                "-fx-font-size: " + (fontSize) + ";";
        
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(5, 5, 5, 10));
        content.setSpacing(0);
        content.setStyle(style);

        FontIcon instrumentIcon = new FontIcon(instrument.getIcon().getIconCode());
        instrumentIcon.setIconSize(fontSize);
        instrumentIcon.setIconColor(instrument.getColor());

        Label instrumentLbl = new Label(instrument.getName().getEnglishTranslation() + " -");
        instrumentLbl.setStyle(
                "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor()) + ";" +
                "-fx-font-size: " + fontSize + ";"
        );

        content.getChildren().addAll(instrumentIcon, instrumentLbl);
        content.setSpacing(5);
        return content;
    }

    private static Node buildClefNode(Clef clef, int fontSize) {
        String style = "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getWhiteColor())+ ";" +
                "-fx-font-size: " + (fontSize) + ";";
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(5, 10, 5, 5));
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
