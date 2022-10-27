package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

public class RootComponent extends BorderPane {

    public RootComponent(Stage stage) {
        TopToolbar topToolbar = new TopToolbar(stage);
        setTop(topToolbar);
        
        String backgroundStyle = ("-fx-background-color: linear-gradient(to top," +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark2) + " 80%, " +
                ThemeManager.hexColorFrom(Theme.Category.Primary, Theme.Shade.Light1) + ");" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10 10 15 15;" +
                "-fx-border-color: " + "grey" + ";");

        setStyle(backgroundStyle + "-fx-background-radius: 15");

        topToolbar.stageMaximizedProperty().addListener((o, oldB, newB) -> {
            setStyle(backgroundStyle + (newB ? "-fx-background-radius: 0;" : "-fx-background-radius: 15;"));
        });
    }
}
