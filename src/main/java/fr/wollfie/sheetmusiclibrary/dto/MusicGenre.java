package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rock, Pop, Jazz, Baroque, ...
 */
public final class MusicGenre extends MetadataObject {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    private final String name;
    private final Optional<Integer> yearBegin;
    private final Optional<Integer> yearEnd;
    private final Color color;

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTOR                                    ||
// ||                                                                                      ||
// \\======================================================================================//
    

    /**
     * @param name      Name of the music genre
     * @param yearBegin The year the music genre began
     * @param yearEnd   The year the music genre faded out
     * @param color     The color the music genre is associated with
     */
    public MusicGenre(
            String name,
            Optional<Integer> yearBegin,
            Optional<Integer> yearEnd,
            Color color
    ) {
        this.name = name;
        this.yearBegin = yearBegin;
        this.yearEnd = yearEnd;
        this.color = color;
    }

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       GETTERS                                        ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Override
    public List<String> getSearchableTokenFields() {
        List<String> result = new ArrayList<>();
        result.add(name);
        yearBegin.ifPresent(value -> result.add(String.valueOf(value)));
        yearEnd.ifPresent(value -> result.add(String.valueOf(value)));

        return result;
    }

    public String getName() {
        return name;
    }
    public Optional<Integer> getYearBegin() {
        return yearBegin;
    }
    public Optional<Integer> getYearEnd() {
        return yearEnd;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MusicGenre[" +
                "name=" + name + ", " +
                "yearBegin=" + yearBegin + ", " +
                "yearEnd=" + yearEnd + ", " +
                "color=" + color + ']';
    }


}
