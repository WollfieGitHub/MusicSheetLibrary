package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collections;
import java.util.List;

/**
 * Video Game, Anime Series, Movies, Classic...
 */
public final class MusicCategory extends MetadataObject {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    private String name;
    private Color color;
    private FontIcon icon;

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTOR                                    ||
// ||                                                                                      ||
// \\======================================================================================//
    

    /**
     * @param name  Name of the category
     * @param color The color the category is associated with
     * @param icon  The icon displayed for the instrument
     */
    @JsonCreator public MusicCategory(
            @JsonProperty("name") String name,
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
        return Collections.singletonList(name);
    }

    @JsonProperty("name") public String getName() {
        return name;
    }
    @JsonProperty("color") public Color getColor() {
        return color;
    }
    @JsonProperty("icon") public FontIcon getIcon() {
        return icon;
    }

    @JsonProperty("name") public void setName(String name) { this.name = name; }
    @JsonProperty("color") public void setColor(Color color) { this.color = color; }
    @JsonProperty("icon") public void setIcon(FontIcon icon) { this.icon = icon; }

    @Override
    public String toString() {
        return "MusicCategory[" +
                "name=" + name + ", " +
                "color=" + color + ", " +
                "icon=" + icon + ']';
    }


}
