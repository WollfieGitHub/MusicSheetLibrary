package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Video Game, Anime Series, Movies, Classic...
 * @param name Name of the category 
 * @param color The color the category is associated with
 */
public record MusicCategory(
        String name,
        Color color
) implements Metadata {
    
    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicCategory that = (MusicCategory) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
