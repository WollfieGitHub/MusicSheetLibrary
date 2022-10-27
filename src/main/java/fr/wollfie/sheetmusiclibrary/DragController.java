package fr.wollfie.sheetmusiclibrary;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.stage.Stage;

public class DragController {

    private final Stage stage;

    private double x0;
    private double y0;
    
    private boolean isDragged;
    
    public DragController(Stage stage) {
        this.stage = stage;
        init();
    }

    public static DragController createFrom(Stage stage) {
        Preconditions.checkNotNull(stage.getScene());
        return new DragController(stage);
    }

    private void init() {
        stage.getScene().setOnMousePressed(mouseDragEvent -> {
            x0 = stage.getX() - mouseDragEvent.getScreenX();
            y0 = stage.getY() - mouseDragEvent.getScreenY();
            
            isDragged = true;
        });
        
        stage.getScene().setOnMouseDragged(mouseEvent -> {
            stage.setX(x0 + mouseEvent.getScreenX());
            stage.setY(y0 + mouseEvent.getScreenY());
        });
        
        stage.getScene().setOnMouseReleased(mouseDragEvent -> {
            isDragged = false;
        });
    }
}
