package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Rock, Pop, Jazz, Baroque, ...
 * @param name Name of the music genre
 * @param yearBegin The year the music genre began
 * @param yearEnd The year the music genre faded out
 * @param color The color the music genre is associated with
 */
public record MusicGenre (
    String name,
    Optional<Integer> yearBegin,
    Optional<Integer> yearEnd,
    Color color
) implements Metadata {

    @Override
    public List<String> getSearchableTokenFields() {
        List<String> result = new ArrayList<>();
        result.add(name);
        yearBegin.ifPresent(value -> result.add(String.valueOf(value)));
        yearEnd.ifPresent(value -> result.add(String.valueOf(value)));
        
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicGenre that = (MusicGenre) o;

        if (!name.equals(that.name)) return false;
        return yearBegin.equals(that.yearBegin);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + yearBegin.hashCode();
        return result;
    }
}
