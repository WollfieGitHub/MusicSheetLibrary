package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import javafx.scene.layout.Pane;

public class CreatorDisplayHandler {

    private static Pane root;

    private static MetadataCreator<?> activeCreator = null;
    
    private CreatorDisplayHandler() { }

    public static void init(Pane root) {
        root.setStyle("-fx-background-color: transparent;");
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
        creator.mounted();
    }

    public static void hideCreator() {
        root.getChildren().remove(activeCreator);
        activeCreator =  null;
    }

}
