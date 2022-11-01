package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.util.function.Consumer;

public class CreatorDisplayHandler {

    private static Node root;

    private MetadataCreator<?> activeCreator = null;
    
    private CreatorDisplayHandler() { }

    public void init(Node root) {
        CreatorDisplayHandler.root = root;
    }
    
    public void promptCreationFor(MetadataCreator<?> creator) {
        if (activeCreator != null) { throw new UnsupportedOperationException("Cannot display creator when" +
                " one is already displayed"); }
        activeCreator = creator;
        display(creator);
    }

    private void display(MetadataCreator<?> creator) {
        
    }

    public void hideCreator() {
        activeCreator =  null;
    }

}
