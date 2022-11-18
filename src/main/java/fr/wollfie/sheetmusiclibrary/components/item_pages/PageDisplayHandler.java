package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.components.CenterSceneContent;
import fr.wollfie.sheetmusiclibrary.components.RootComponent;
import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.*;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.UIMode;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.exceptions.Errors;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.BindingUtil;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class PageDisplayHandler extends StackPane implements CenterSceneContent {
    
    private final ObjectProperty<UIMode> mode = new SimpleObjectProperty<>(UIMode.READ_ONLY);
    
    public static PageDisplayHandler createNew() {
        return new PageDisplayHandler();
    }

    public PageDisplayHandler() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(50, 0, 5, 0));
    }

    public <M extends Metadata> void displayContentFor(M item) {
        getChildren().setAll(((DisplayAdapter<M>)item.getType().displayAdapter)
                .getPageRepresentation(item, mode));
    }

    @Override
    public Collection<Node> getContextControls() {
        return Arrays.asList(
                new ClickableFontIcon(MaterialDesignB.BOOKSHELF,
                ThemeManager.getWhiteColor(), FontSize.DEFAULT_MEDIUM_ICON,
                RootComponent::displayLibrary),
                BindingUtil.bindNodeTo(this.mode, this::getUIModeIcon)
        );
    }
  
    private FontIcon getUIModeIcon(UIMode mode) {
        return switch (mode) {
            case EDIT -> new ClickableFontIcon(
                    MaterialDesignP.PENCIL,
                    ThemeManager.getWhiteColor(),FontSize.DEFAULT_MEDIUM_ICON,
                    this::nextUIMode
            );
            case READ_ONLY -> new ClickableFontIcon(
                    MaterialDesignB.BOOK,
                    ThemeManager.getWhiteColor(), FontSize.DEFAULT_MEDIUM_ICON,
                    this::nextUIMode
            );
            case default -> Errors.ERROR_ICON;
        };
    }

    private void nextUIMode() {
        UIMode[] modes = UIMode.values(); 
        this.mode.setValue(modes[(this.mode.getValue().ordinal() + 1) % modes.length]);
    }
}
