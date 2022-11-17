package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.components.CenterSceneContent;
import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.*;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;

import java.util.Collection;
import java.util.Collections;

public class PageDisplayHandler extends StackPane implements CenterSceneContent {
    
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

    @Override
    public Collection<Node> getContextControls() {
        return Collections.singleton(
                new ClickableFontIcon(MaterialDesignB.BOOKSHELF,
                ThemeManager.getWhiteColor(), FontSize.DEFAULT_MEDIUM_ICON,
                RootComponent::displayLibrary)
        );
    }
}
