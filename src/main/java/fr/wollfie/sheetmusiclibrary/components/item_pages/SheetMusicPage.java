package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import fr.wollfie.sheetmusiclibrary.components.display_adapters.TrackDisplayAdapter;
import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.*;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public final class SheetMusicPage extends MetadataPage<SheetMusic> {

    /**
     * Builds a page to display the given item's info and possibly edit
     * their value
     *
     * @param item The item to display as a content
     */
    public SheetMusicPage(SheetMusic item, ObjectProperty<UIMode> uiModeProperty) {
        super(item, uiModeProperty);
    }

    @Override
    protected void initComponent(VBox vBox) {
        HBox titleHBox = buildTitleHBox();
        FlowPane metaInfoHBox = buildMetaInfoHBox();
        HBox filesHBox = buildFilesHBox();
        
        vBox.getChildren().addAll(titleHBox, metaInfoHBox, filesHBox);
    }

    private FlowPane buildMetaInfoHBox() {
        FlowPane buildInfoHBox = new FlowPane();
        buildInfoHBox.setPadding(new Insets(0, 20, 0, 20));
        buildInfoHBox.getChildren().addAll(
                item.getTracks().stream()
                        .map(TrackDisplayAdapter::getItemRepresentationGiven)
                        .toList()
        );
        buildInfoHBox.setHgap(10);
        buildInfoHBox.setVgap(10);
        return buildInfoHBox;
    }

    @SuppressWarnings("unchecked")
    private HBox buildTitleHBox() {
        EditableValue<String> titleLabel = FieldEditor.synchronize(
                new EditableLabel(uiModeProperty, 
                        Theme.Category.Accent, FontSize.DEFAULT_H0,
                        false, Pos.BOTTOM_LEFT
                ), item::setName, item::getName
        );
        
        EditableValue<Artist> editableArtist = FieldEditor.synchronize(
                new EditableArtist(uiModeProperty),
                FieldAdapter.<Artist, MetadataRef<Artist>>adapt(item::setArtistRef, MetadataRef::new),
                FieldAdapter.adapt(item::getArtistRef, MetadataRef::getValue)
        );
        
        HBox titleHBox = new HBox(editableArtist, titleLabel);
        titleHBox.setAlignment(Pos.BOTTOM_LEFT);
        titleHBox.setPadding(new Insets(20));
        titleHBox.setSpacing(10);
        titleHBox.setStyle(
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-color: " + Utils.toRGBCode(ThemeManager.getWhiteColor()) + ";"
        );
        return titleHBox;
    }

    private HBox buildFilesHBox() {
        HBox filesHBox = new HBox();
        filesHBox.setAlignment(Pos.CENTER);

        FontIcon pdfIcon = new ClickableFontIcon(MaterialDesignF.FILE_PDF_OUTLINE, ThemeManager.getWhiteColor(), FontSize.DEFAULT_BIG_ICON,
                () -> { if (item.getPdfFile() != null) {
                    item.getPdfFile().open();
                } });
        FontIcon musescoreIcon = new ClickableFontIcon(MaterialDesignF.FILE_MUSIC_OUTLINE, ThemeManager.getWhiteColor(), FontSize.DEFAULT_BIG_ICON,
                () -> { if (item.getMusescoreFile() != null) {
                    item.getMusescoreFile().open();
                } });
        filesHBox.getChildren().addAll(pdfIcon, musescoreIcon);
        return filesHBox;
    }


}
