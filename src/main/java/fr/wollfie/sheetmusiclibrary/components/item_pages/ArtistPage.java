package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.LibraryWrapper;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.MetadataLibraryDisplay;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedLabel;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.*;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Curve;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.NodeColorFinder;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ArtistPage extends MetadataPage<Artist> {

    private StackPane bannerWrapper;

    /**
     * Builds a page to display the given item's info and possibly edit
     * their value
     *
     * @param item The item to display as a content
     */
    public ArtistPage(Artist item, ObjectProperty<UIMode> uiModeProperty) {
        super(item, uiModeProperty);
    }
    
    @Override
    protected void initComponent(VBox vBox) {

        item.getImageUrl().imageProperty().addListener(inv -> reloadBanner());
        bannerWrapper = new StackPane(getBanner());
        bannerWrapper.setAlignment(Pos.CENTER);
        bannerWrapper.setPadding(new Insets(0));

        LibraryWrapper libraryWrapper = new LibraryWrapper();
        libraryWrapper.setContent(new MetadataLibraryDisplay<>(FXCollections.observableList(
                SheetMusicLibrary.getAllSheetMusicWith(item)),
                SheetMusic.class));
        libraryWrapper.setPadding(new Insets(0, 0, 0, 20));

        vBox.getChildren().addAll(bannerWrapper, libraryWrapper);
    }

    // Reloads the artist's picture and all that kind of stuff
    private void reloadBanner() {
        bannerWrapper.getChildren().setAll(getBanner());
    }

    private HBox getBanner() {
        EditableValue<String> firstNameOrNickname = FieldEditor.synchronize(
                new EditableLabel(
                        uiModeProperty,
                        Theme.Category.Accent, FontSize.DEFAULT_H0,
                        Pos.BOTTOM_LEFT),
                item::setName,
                item::getName
        );

        HBox datesHBox = getDatesHBox();

        VBox nameAndAge = new VBox(firstNameOrNickname, datesHBox);
        nameAndAge.setAlignment(Pos.BOTTOM_LEFT);
        nameAndAge.setSpacing(10);

        StackPane picWrapper = new StackPane();

        Image profilePicture = item.getImageUrl().getImage();
        if (profilePicture != null) {
            ImageView profilePictureDisplay = new ImageView(profilePicture);
            profilePictureDisplay.setFitHeight(FontSize.DEFAULT_H1 * 6);
            profilePictureDisplay.setPreserveRatio(true);

            picWrapper = new StackPane(profilePictureDisplay);
        }

        picWrapper.setPadding(new Insets(5, 5, 5, 20));
        picWrapper.setAlignment(Pos.CENTER);

        HBox banner = new HBox(picWrapper, nameAndAge);
        banner.setAlignment(Pos.BOTTOM_LEFT);
        banner.setSpacing(10);

        Color picColor = NodeColorFinder.getAverageColorOf(profilePicture);
        String background = Utils.generateGradient(
                Utils.BackgroundDirection.TO_TOP,
                picColor, Color.TRANSPARENT,
                Curve.EXPONENTIAL, 50
        );
        
        banner.setStyle(
                "-fx-background-color: " + background + ";" +
                "-fx-border-color: " + Utils.toRGBCode(picColor.deriveColor(
                        0, 1.75, 3, 1
                )) + ";" +
                "-fx-border-width: 0 0 1 0;"
        );
        return banner;
    }

    private HBox getDatesHBox() {
        int fontSize = FontSize.DEFAULT_H2;
        EditableValue<Integer> dateBirth = FieldEditor.synchronize(
                new EditableInteger(
                        uiModeProperty, Theme.Category.Accent, fontSize, false
                ), item::setYearOfBirth, item::getYearOfBirth
        );
        EditableValue<Integer> dateDeath = FieldEditor.synchronize(
                new EditableInteger(
                        uiModeProperty, Theme.Category.Accent, fontSize, false
                ), item::setYearOfDeath, item::getYearOfDeath
        );

        ThemedLabel separatorLbl = new ThemedLabel(" - ", fontSize);
        
        
        HBox datesHBox = new HBox(dateBirth, separatorLbl, dateDeath);

        dateDeath.valueProperty().addListener((i, oldV, newV) -> {
            List<Node> nodes = new ArrayList<>();
            nodes.add(dateBirth);
            if (newV != null) { nodes.add(separatorLbl); }
            nodes.add(dateDeath);
            
            datesHBox.getChildren().setAll(nodes.toArray(new Node[0]));
        });
        
        datesHBox.setStyle(
                "-fx-text-fill: " + Utils.toRGBCode(ThemeManager.getTextColorFrom(null).darker()) + ";"
        );

        return datesHBox;
    }
}
