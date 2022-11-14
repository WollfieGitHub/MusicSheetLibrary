package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.*;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PageDisplayHandler extends StackPane {
    
    public static PageDisplayHandler createNew() {
        return new PageDisplayHandler();
    }

    public PageDisplayHandler() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(50, 0, 5, 0));
    }

    public <M extends Metadata> void displayContentFor(M item) {
        getChildren().setAll(((DisplayAdapter<M>)item.getType().displayAdapter).getPageRepresentation(item));
    }
}
