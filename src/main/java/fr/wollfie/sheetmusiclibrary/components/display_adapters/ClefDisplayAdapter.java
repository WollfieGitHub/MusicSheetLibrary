package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.UIMode;
import fr.wollfie.sheetmusiclibrary.dto.Clef;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

public final  class ClefDisplayAdapter extends DisplayAdapter<Clef> {
    
    @Override
    public Node getItemRepresentation(Clef clef) {
        FontIcon clefIcon = new FontIcon(clef.fontIcon.getIconCode());
        clefIcon.setIconSize(FontSize.DEFAULT_BIG_ICON);
        clefIcon.setIconColor(ThemeManager.getTextColorFrom(null));
        return clefIcon;
    }

    @Override
    public MetadataPage<Clef> getPageRepresentation(Clef clef, ObjectProperty<UIMode> uiModeProperty) {
        return null;
    }

}
