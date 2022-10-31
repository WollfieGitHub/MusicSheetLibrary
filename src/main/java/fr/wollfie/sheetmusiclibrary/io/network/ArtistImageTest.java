package fr.wollfie.sheetmusiclibrary.io.network;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class ArtistImageTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        stackPane.getChildren().add(new ImageView(ArtistImageRetriever.fetchFor(
                new Artist("Ludwig Van", "Beethoven", 199, Optional.empty())
        )));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
