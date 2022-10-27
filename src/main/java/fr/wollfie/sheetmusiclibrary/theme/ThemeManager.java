package fr.wollfie.sheetmusiclibrary.theme;

import fr.wollfie.sheetmusiclibrary.io.FileSystem;
import fr.wollfie.sheetmusiclibrary.io.serialization.SerializationEngine;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.File;
import java.io.IOException;

import static fr.wollfie.sheetmusiclibrary.theme.Theme.*;

public class ThemeManager {
    
    private static final ObjectProperty<Theme> theme = new SimpleObjectProperty<>();
    private static final Theme DEFAULT_THEME = new Theme(
            new Color(0.11372549, 0.7254902, 0.32941177, 1),
            new Color(0.07058824, 0.07058824, 0.07058824, 1),
            new Color(237/255.0, 45/255.0, 128/255.0, 1),
            new Color(75/255.0, 209/255.0, 171/255.0, 1),
            new Color(0.07058824, 0.07058824, 0.07058824, 1),
            0.7, 0.35, 0.16, 0.05,
            new Color(0.9, 0.9, 0.9, 1)
    );

    public static Theme getTheme() { return theme.get(); }
    public static void setTheme(Theme theme) { ThemeManager.theme.set(theme); }
    
    public static void init() throws IOException {
        Theme recoveredTheme = SerializationEngine.loadFrom(new File(FileSystem.BASE_PATH, "theme.json"), Theme.class);
        
        if (recoveredTheme != null) { theme.set(recoveredTheme); }
        else { theme.set(DEFAULT_THEME); }
    }
    
    public static Color colorFrom(Category category, Shade shade) {
        
        Color result = switch (category) {
            case Primary -> getTheme().primaryColor();
            case Accent -> getTheme().accentColor();
            case Error -> getTheme().errorColor();
            case Confirm -> getTheme().confirmColor();
            case Background -> getTheme().backgroundColor();
        };
        
        double factor = switch (shade) {
            case Dark2 -> 0 + getTheme().shadeDark2();
            case Dark1 -> 0 + getTheme().shadeDark1();
            case Default -> 1;
            case Light1 -> 1 + getTheme().shadeLight1();
            case Light2 -> 1 + getTheme().shadeLight2();
        };
        
        return result.deriveColor(0, 1, factor, 1);
    }

    public static Paint getWhiteColor() {
        return theme.get().whiteColor();
    }
}
