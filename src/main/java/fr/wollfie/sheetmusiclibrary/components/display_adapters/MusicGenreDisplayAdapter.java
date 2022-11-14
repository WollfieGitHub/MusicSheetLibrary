package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.Card;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableCard;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.dto.MusicGenre;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MusicGenreDisplayAdapter extends DisplayAdapter<MusicGenre> {
    
    @Override
    public Node getItemRepresentation(MusicGenre genre) {
        return new ClickableCard(
                null,
                getDisplayText(genre)
        );
    }

    @Override
    public MetadataPage<MusicGenre> getPageRepresentation(MusicGenre genre) {
        return null;
    }

    private static Node getDisplayText(MusicGenre genre) {
        Label nameLbl = new ClickableLabel(genre.getName());

        nameLbl.setStyle(
                "-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";"
        );

        Label yearLbl = new Label(
                genre.getYearBegin().map(String::valueOf).orElse("") + " - " +
                        genre.getYearEnd().map(String::valueOf).orElse("")
        );

        yearLbl.setStyle(
                "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H3 + ";"
        );


        VBox vBox = new VBox(nameLbl, yearLbl);
        vBox.setAlignment(Pos.CENTER_LEFT);
        return vBox;
    }

}
