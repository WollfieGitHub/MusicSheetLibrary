package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import javafx.scene.control.ScrollPane;

public class LibraryWrapper extends ScrollPane {

    public LibraryWrapper() {
        setMaxWidth(Double.MAX_VALUE);
        setFitToWidth(true);
        setStyle("-fx-background: rgba(0, 0, 0, 0);" +
                "-fx-background-color: rgba(0, 0, 0, 0);");
    }
}
