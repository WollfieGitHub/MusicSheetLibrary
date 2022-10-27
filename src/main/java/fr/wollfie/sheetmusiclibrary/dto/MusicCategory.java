package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;

/**
 * Video Game, Anime Series, Movies, Classic...
 */
public record MusicCategory(
        String name,
        Color color
) implements Metadata { }
