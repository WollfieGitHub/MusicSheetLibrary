package fr.wollfie.sheetmusiclibrary.exceptions;

import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;

public class Errors {
    private Errors() {}
    
    public static final Color ERROR_COLOR = Color.FUCHSIA;
    public static final FontIcon ERROR_ICON = new ClickableFontIcon(
            MaterialDesignC.CELTIC_CROSS,
            ERROR_COLOR, FontSize.DEFAULT_MEDIUM_ICON,
            () -> Logger.error("This clickable icon is an error !")
    );
}
