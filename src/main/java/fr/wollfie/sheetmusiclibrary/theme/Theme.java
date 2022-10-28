package fr.wollfie.sheetmusiclibrary.theme;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;
import javafx.scene.paint.Color;

public record Theme(
    Color primaryColor,
    Color accentColor,
    Color errorColor,
    Color confirmColor,
    Color backgroundColor,
    double shadeLight1,
    double shadeLight2,
    double shadeDark1,
    double shadeDark2,
    Color whiteColor
) implements JsonSerializable {
    
    public enum Shade {
        Dark2, Dark1, Default, Light1, Light2
    }
    
    public enum Category {
        Primary, Accent, Error, Confirm, Background
    }
    
}
