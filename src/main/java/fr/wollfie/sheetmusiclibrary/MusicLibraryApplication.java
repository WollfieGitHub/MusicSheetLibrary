package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.MusicSheetLibraryDisplay;
import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.controllers.DragController;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class MusicLibraryApplication extends Application {
    
    public static final String APP_NAME = "Sheet Music Library";

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        ThemeManager.init();
        Logger.infof("Logger level set to %s", Logger.getCurrentLevel());
        
        BorderPane root = new RootComponent(stage);
        root.getStylesheets().addAll(
                "noheader.css",
                "main.css",
                "scrollbar.css"
        );

        SheetMusicLibrary.setLocationAndInit(SheetMusicLibrary.DEFAULT_LOCATION);
        
        SheetMusicLibrary.insert(new Instrument("Cello", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new Instrument("Piano", Color.BLACK, new FontIcon("mdi2p-piano")));
        SheetMusicLibrary.insert(new Instrument("Saxophone", Color.YELLOW, new FontIcon("mdi2s-saxophone")));
        SheetMusicLibrary.insert(new Instrument("Trumpet", Color.YELLOW, new FontIcon("mdi2t-trumpet")));
        SheetMusicLibrary.insert(new Instrument("Guitar", Color.RED, new FontIcon("mdi2g-guitar-acoustic")));
        SheetMusicLibrary.insert(new Instrument("Mandoline", Color.RED, new FontIcon("mdi2g-guitar-acoustic")));
        SheetMusicLibrary.insert(new Instrument("Bassoon", Color.RED, new FontIcon("mdi2g-guitar-acoustic")));
        SheetMusicLibrary.insert(new Instrument("Violin", Color.SADDLEBROWN.brighter().brighter(), new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new Instrument("Viola", Color.SADDLEBROWN.brighter(), new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new Instrument("Drum kit", Color.RED, new FontIcon("mdi2g-guitar-acoustic")));
        SheetMusicLibrary.insert(new Instrument("Double Bass", Color.SADDLEBROWN.darker(), new FontIcon("mdi2v-violin")));
        
        SheetMusicLibrary.insert(new Artist("Ludwig van", "Beethoven", 0, Optional.empty()));
        SheetMusicLibrary.insert(new Artist("Alec", "Benjamin", 0, Optional.empty()));
        SheetMusicLibrary.insert(new Artist("Hanz", "Zimmer", 0, Optional.empty()));
        SheetMusicLibrary.insert(new Artist("Billie", "Eilish", 0, Optional.empty()));
        SheetMusicLibrary.insert(new Artist("Twenty One Pilots", "", 0, Optional.empty()));

        SheetMusicLibrary.insert(new MusicCategory("Video Games", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new MusicCategory("Movie", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new MusicCategory("Series", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new MusicCategory("Anime", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new MusicCategory("Classical", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        SheetMusicLibrary.insert(new MusicCategory("Popular", Color.SADDLEBROWN, new FontIcon("mdi2v-violin")));
        
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