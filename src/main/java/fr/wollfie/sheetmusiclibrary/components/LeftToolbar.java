package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.CategorySelectionBar;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.*;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class LeftToolbar extends VBox {

    public LeftToolbar() {
        FontIcon fontIcon = new FontIcon("mdi2p-plus-circle-outline");
        ThemedButton themedButton = new ThemedButton("Add new", fontIcon, Theme.Category.Transparent, 20);
        themedButton.setCornerRadius(50);
        themedButton.setOnAction(clickEvent -> {
            MetadataCreator<?> creator = switch (CategorySelectionBar.currentSelectedType) {
                case Instrument -> new InstrumentCreator(SheetMusicLibrary::tryInsert);
                case MusicGenre -> new MusicGenreCreator(SheetMusicLibrary::tryInsert);
                case MusicCategory -> new MusicCategoryCreator(SheetMusicLibrary::tryInsert);
                default -> null; // TODO
            };
            if (creator == null) {
                return;
            }
            CreatorDisplayHandler.promptCreationFor(creator);
         });
        
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20, 20, 0, 20));
        setStyle("-fx-background-color: " + ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + ";" +
                "-fx-background-radius: 0 0 0 25;");
        
        getChildren().setAll(themedButton);
    }
}
