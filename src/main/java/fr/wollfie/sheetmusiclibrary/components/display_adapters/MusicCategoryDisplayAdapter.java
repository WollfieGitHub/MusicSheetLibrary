package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.Card;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableCard;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public final class MusicCategoryDisplayAdapter extends DisplayAdapter<MusicCategory> {
    
    @Override
    public Node getItemRepresentation(MusicCategory category) {
        return new ClickableCard(
                getIcon(category),
                getName(category)
        );
    }

    @Override
    public MetadataPage<MusicCategory> getPageRepresentation(MusicCategory category) {
        return null;
    }

    private static Node getIcon(MusicCategory category) {
        FontIcon icon = category.getIcon();
        icon.setIconColor(category.getColor());
        icon.setIconSize(FontSize.DEFAULT_BIG_ICON);
        return icon;
    }

    private static Node getName(MusicCategory category) {
        Label label = new ClickableLabel(category.getName());
        label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                "-fx-font-size: " + FontSize.DEFAULT_H2 + ";");
        return label;
    }

}
