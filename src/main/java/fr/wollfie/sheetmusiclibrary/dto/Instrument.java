package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.wollfie.sheetmusiclibrary.utils.LingualString;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * A musical instruments the Sheet Music contains direction for
 */
public final class Instrument extends MetadataObject {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    private final LingualString name;
    private final Color color;
    private final FontIcon icon;

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTOR                                    ||
// ||                                                                                      ||
// \\======================================================================================//
    
    /**
     * @param name  The name of the instruments
     * @param color The color with which the instruments are associated with
     * @param icon  The icon the instruments is associated with
     */
    @JsonCreator
    public Instrument(
            @JsonProperty("name") LingualString name,
            @JsonProperty("color") Color color,
            @JsonProperty("icon") FontIcon icon
    ) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       GETTERS                                        ||
// ||                                                                                      ||
// \\======================================================================================//

    @Override
    public List<String> getSearchableTokenFields() {
        return new ArrayList<>(name.getAllTranslations());
    }
    @Override
    public String toString() {
        return "Instrument{" + "name='" + name + '\'' + '}';
    }
    public LingualString getName() {
        return name;
    }
    public Color getColor() {
        return color;
    }
    public FontIcon getIcon() {
        return icon;
    }

}
