package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.controllers.editable_field.EditableLabel;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.EditableValue;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.FieldEditor;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Curve;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.NodeColorFinder;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ArtistPage extends MetadataPage<Artist> {
    /**
     * Builds a page to display the given item's info and possibly edit
     * their value
     *
     * @param item The item to display as a content
     */
    public ArtistPage(Artist item) {
        super(item);
    }
    

    @Override
    protected void initComponent(VBox vBox) {

        HBox banner = getBanner();

        vBox.getChildren().addAll(banner);
    }

    private HBox getBanner() {
        EditableValue<String> firstNameOrNickname = FieldEditor.synchronize(
                new EditableLabel(Theme.Category.Accent, FontSize.DEFAULT_H0, Pos.BOTTOM_LEFT),
                item::setFirstNameOrNickname,
                item::getFirstNameOrNickname
        );

        EditableValue<String> lastName = FieldEditor.synchronize(
                new EditableLabel(Theme.Category.Accent, FontSize.DEFAULT_H0, Pos.BOTTOM_LEFT),
                item::setLastName, item::getLastName
        );

        HBox names = new HBox(firstNameOrNickname, lastName);
        names.setAlignment(Pos.BOTTOM_LEFT);
        names.setSpacing(10);

        Image profilePicture = item.getImageUrl().getImage();
        ImageView profilePictureDisplay = new ImageView(profilePicture);
        profilePictureDisplay.setFitHeight(FontSize.DEFAULT_H1 * 6);
        profilePictureDisplay.setPreserveRatio(true);

        StackPane picWrapper = new StackPane(profilePictureDisplay);
        picWrapper.setPadding(new Insets(5, 5, 5, 20));
        picWrapper.setAlignment(Pos.CENTER);

        HBox banner = new HBox(picWrapper, names);
        banner.setAlignment(Pos.BOTTOM_LEFT);
        banner.setSpacing(10);

        Color picColor = NodeColorFinder.getAverageColorOf(profilePicture);
        String background = Utils.generateGradient(
                "to top",
                picColor, Color.TRANSPARENT,
                Curve.EXPONENTIAL, 50
        );
        Logger.info(Utils.toRGBCode(picColor.deriveColor(
                0, 1.75, 3, 1
        )));
        banner.setStyle(
                "-fx-background-color: " + background + ";" +
                "-fx-border-color: " + Utils.toRGBCode(picColor.deriveColor(
                        0, 1.75, 3, 1
                )) + ";" +
                "-fx-border-width: 0 0 1 0;"
        );
        return banner;
    }
}
