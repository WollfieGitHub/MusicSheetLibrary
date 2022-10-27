package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.Icon;

public record Instrument(
        String name,
        Color color,
        Icon icon
)  implements Metadata { }
