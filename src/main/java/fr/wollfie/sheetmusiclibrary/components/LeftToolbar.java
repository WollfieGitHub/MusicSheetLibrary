package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.CreatorDisplayHandler;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.InstrumentCreator;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class LeftToolbar extends VBox {

    public LeftToolbar() {
        FontIcon fontIcon = new FontIcon("mdi2p-plus-circle-outline");
        ThemedButton themedButton = new ThemedButton("Add new", fontIcon, Theme.Category.Transparent, 20);
        themedButton.setCornerRadius(50);
        themedButton.setOnAction(clickEvent -> {
            CreatorDisplayHandler.promptCreationFor(new InstrumentCreator(Logger::info));
        });
        
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20, 20, 0, 20));
        setStyle("-fx-background-color: " + ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + ";" +
                "-fx-background-radius: 0 0 0 25;");
        
        getChildren().setAll(themedButton);
    }
}
