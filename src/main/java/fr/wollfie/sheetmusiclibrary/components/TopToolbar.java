package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.controllers.DangerButton;
import fr.wollfie.sheetmusiclibrary.MusicLibraryApplication;
import fr.wollfie.sheetmusiclibrary.controllers.EditableLabel;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class TopToolbar extends HBox {
    
    private static final FontIcon minimizeIcon = new FontIcon("fas-window-minimize");
    private static final FontIcon restoreIcon = new FontIcon("far-window-restore");
    private static final FontIcon maximizeIcon = new FontIcon("far-window-maximize");
    private static final FontIcon closeIcon = new FontIcon("far-window-close");

    private BooleanProperty stageMaximized = new SimpleBooleanProperty();

    public ReadOnlyBooleanProperty stageMaximizedProperty() { return stageMaximized; }

    public TopToolbar(Stage stage) {
        minimizeIcon.setIconColor(ThemeManager.getWhiteColor());
        restoreIcon.setIconColor(ThemeManager.getWhiteColor());
        maximizeIcon.setIconColor(ThemeManager.getWhiteColor());
        closeIcon.setIconColor(ThemeManager.getWhiteColor());
        
        ThemedButton minimizeButton = new ThemedButton(null, minimizeIcon, Theme.Category.Background);
        ThemedButton resizeButton = new ThemedButton(null, maximizeIcon, Theme.Category.Background);
        ThemedButton closeButton = new DangerButton(null, closeIcon);
        
        HBox base = new HBox(minimizeButton, resizeButton, closeButton);
        base.setAlignment(Pos.CENTER);
        base.setSpacing(5);
        
        setStyle("-fx-background-color: " + ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + ";" +
                "-fx-background-radius: 11 11 0 0");
        
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(10));
        
        getChildren().addAll(base);
        
        //- == == Events == == -//
        minimizeButton.setOnMouseClicked(unused -> {
            stage.setIconified(true);
        });
        
        resizeButton.setOnMouseClicked(unused -> {
            stageMaximized.set(!stageMaximized.get());
            stage.setMaximized(stageMaximized.get());
            resizeButton.setGraphic(stageMaximized.get() ? restoreIcon : maximizeIcon);
        });
        
        closeButton.setOnMouseClicked(unused -> MusicLibraryApplication.quit());
    }
}
