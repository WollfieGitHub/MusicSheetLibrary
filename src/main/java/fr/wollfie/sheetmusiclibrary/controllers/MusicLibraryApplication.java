package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.DragController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MusicLibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
        
        stage.setTitle("Music Library");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        DragController dragController = DragController.createFrom(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}