package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RootComponent extends BorderPane {

    public RootComponent(Stage stage) {
        TopToolbar topToolbar = new TopToolbar(stage);
        
        setTop(topToolbar);
        
        // Set light thin borders
        String borderStyle = "-fx-border-width: 1;" +
                "-fx-border-radius: 10 10 15 15;" +
                "-fx-border-color: " + "grey" + ";";
        
        // Background style is a gradient from black to the upper lighter primary color 
        String backgroundStyle = ("-fx-background-color: linear-gradient(to top," +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark2) + ", " +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + " 40%, " +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Default) + " 50%, " +
                ThemeManager.hexColorFrom(Theme.Category.Primary, Theme.Shade.Dark1) + " 90%, " +
                ThemeManager.hexColorFrom(Theme.Category.Primary, Theme.Shade.Default) + " 110%);");
        
        setStyle(backgroundStyle + borderStyle + "-fx-background-radius: 15");

        // Remove unnecessary florishes when in full screen
        topToolbar.stageMaximizedProperty().addListener((o, oldB, newB) -> {
            setStyle(backgroundStyle + (newB ? "-fx-background-radius: 0;" : borderStyle + "-fx-background-radius: 15;"));
        });
    }
}
