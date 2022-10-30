package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedTextField;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class SubToolbar extends HBox {

    public SubToolbar() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(5));
        
        // Display the path of the MusicLibrary
        ThemedTextField musicLocationTf =
                new ThemedTextField(SheetMusicLibrary.DEFAULT_LOCATION.getAbsolutePath(), Theme.Category.Accent, 10);
        musicLocationTf.prefWidthProperty().bind(widthProperty().multiply(0.8));
        musicLocationTf.setDisable(true);
        
        getChildren().add(musicLocationTf);
    }
}
