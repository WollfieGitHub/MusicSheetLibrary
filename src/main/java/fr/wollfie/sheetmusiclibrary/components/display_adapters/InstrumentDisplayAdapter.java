package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.Card;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public final class InstrumentDisplayAdapter extends DisplayAdapter<Instrument> {

    @Override
    public Node getItemRepresentation(Instrument instrument) {
        return new Card(
                getIcon(instrument),
                getName(instrument)        
        );
    }

    @Override
    public MetadataPage<Instrument> getPageRepresentation(Instrument instrument) {
        return null;
    }

    private static Node getName(Instrument instrument) {
        Label label = new ClickableLabel(instrument.getName().getEnglishTranslation());
        label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";");
        return label;
    }

    private static Node getIcon(Instrument instrument) {
        FontIcon icon = instrument.getIcon();
        icon.setIconColor(instrument.getColor());
        icon.setIconSize(FontSize.DEFAULT_BIG_ICON);
        return icon;
    }

}
