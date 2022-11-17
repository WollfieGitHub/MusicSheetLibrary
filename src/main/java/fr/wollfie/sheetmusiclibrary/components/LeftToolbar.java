package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.CategorySelectionBar;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.*;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.io.IOException;

public class LeftToolbar extends VBox {

    public LeftToolbar() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20, 20, 0, 20));
        setSpacing(10);
        setStyle(
                "-fx-background-color: " + ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + ";" +
                "-fx-background-radius: 0 0 0 25;"
        );
        
        RootComponent.currentContentProperty().addListener((l, oldV, newV) -> {
            getChildren().setAll(newV.getContextControls());
        });
    }
}
