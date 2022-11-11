package fr.wollfie.sheetmusiclibrary.dto;

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
    
    private final String name;
    private final Color color;
    private final FontIcon icon;

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
    public MusicCategory(
            String name,
            Color color,
            FontIcon icon
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

    public String getName() {
        return name;
    }
    public Color getColor() {
        return color;
    }
    public FontIcon getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "MusicCategory[" +
                "name=" + name + ", " +
                "color=" + color + ", " +
                "icon=" + icon + ']';
    }


}
