package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.LibraryDisplay;
import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries.MusicSheetLibraryDisplay;
import fr.wollfie.sheetmusiclibrary.controllers.DragController;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class MusicLibraryApplication extends Application {
    
    public static final String APP_NAME = "Sheet Music Library";

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        
        ThemeManager.init();
        SheetMusicLibrary.setLocationAndInit(SheetMusicLibrary.DEFAULT_LOCATION);

        Logger.infof("Logger level set to %s", Logger.getCurrentLevel());

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: transparent;");
        
        BorderPane base = new RootComponent(primaryStage);
        base.setCenter(new LibraryDisplay());
        base.getStylesheets().addAll(
                "noheader.css",
                "main.css",
                "scrollbar.css",
                "titled_pane.css"
        );
        root.getChildren().add(base);
        
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        DragController dragController = DragController.createFrom(primaryStage);
        
        Logger.info("Application shown");
    }

    public static void main(String[] args) {
        launch();
    }

    public static void quit() {
        primaryStage.hide();
        try {
            cleanupBeforeExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void cleanupBeforeExit() throws IOException {
        SheetMusicLibrary.save();
    }
}