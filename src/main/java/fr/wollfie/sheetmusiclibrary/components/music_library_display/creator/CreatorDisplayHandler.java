package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class CreatorDisplayHandler {

    private static Pane root;

    private static MetadataCreator<?> activeCreator = null;
    
    private CreatorDisplayHandler() { }

    public static void init(Pane root) {
        CreatorDisplayHandler.root = root;
    }
    
    public static void promptCreationFor(MetadataCreator<?> creator) {
        if (activeCreator != null) { throw new UnsupportedOperationException("Cannot display creator when" +
                " one is already displayed"); }
        activeCreator = creator;
        display(creator);
    }

    private static void display(MetadataCreator<?> creator) {
        root.getChildren().add(creator);
    }

    public static void hideCreator() {
        root.getChildren().remove(activeCreator);
        activeCreator =  null;
    }

}
