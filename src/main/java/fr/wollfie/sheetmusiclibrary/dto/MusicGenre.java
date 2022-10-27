package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Rock, Pop, Jazz...
 */
public record MusicGenre (
    String name,
    Optional<Integer> yearBegin,
    Optional<Integer> yearEnd,
    Color color
) implements Metadata { }
