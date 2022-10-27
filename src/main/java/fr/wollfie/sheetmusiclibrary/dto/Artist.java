package fr.wollfie.sheetmusiclibrary.dto;

import java.util.List;
import java.util.Optional;

public record Artist(
        String firstName,
        String lastName,
        int yearOfBirth,
        Optional<Integer> yearOfDeath,
        List<MusicGenre> musicGenres
) implements Metadata { }
