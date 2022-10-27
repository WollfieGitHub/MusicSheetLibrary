package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.DragController;
import fr.wollfie.sheetmusiclibrary.components.TopToolbar;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MusicLibraryApplication extends Application {
    
    public static final String APP_NAME = "Music Sheet Library";

    private static Stage primaryStage;
    
    public static void quit() {
        primaryStage.hide();
        cleanupBeforeExit();
        System.exit(0);
    }

    private static void cleanupBeforeExit() {
        
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        
        ThemeManager.init();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Dark2));
        
        TopToolbar topToolbar = new TopToolbar(primaryStage);
        root.setTop(topToolbar);
        
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        DragController dragController = DragController.createFrom(primaryStage);

        root.setCenter(new ThemedButton("Click me", null, Theme.Category.Primary));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}