package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collections;
import java.util.List;

/**
 * A musical instrument the Sheet Music contains direction for
 * @param name The name of the instrument
 * @param color The color with which the instrument is associated with 
 * @param icon The icon the instrument is associated with
 */
public record Instrument(
        String name,
        Color color,
        FontIcon icon
)  implements Metadata {
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instrument that = (Instrument) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(name);
    }

    @Override
    public String toString() {
        return "Instrument{" + "name='" + name + '\'' + '}';
    }
}
