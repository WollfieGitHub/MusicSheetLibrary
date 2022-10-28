package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.components.MusicSheetLibraryDisplay;
import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.controllers.DragController;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MusicLibraryApplication extends Application {
    
    public static final String APP_NAME = "Sheet Music Library";

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        ThemeManager.init();
        Logger.infof("Logger level set to %s", Logger.getCurrentLevel());
        
        BorderPane root = new RootComponent(stage);
        
        
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(Color.TRANSPARENT);
        
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        DragController dragController = DragController.createFrom(primaryStage);

        root.setCenter(new MusicSheetLibraryDisplay());
        primaryStage.show();
        Logger.info("Application shown");
    }

    public static void main(String[] args) {
        launch();
    }

    public static void quit() {
        primaryStage.hide();
        cleanupBeforeExit();
        System.exit(0);
    }

    private static void cleanupBeforeExit() {

    }
}