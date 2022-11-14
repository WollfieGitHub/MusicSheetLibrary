package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.controllers.ClickableFontIcon;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.EditableLabel;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.EditableValue;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.FieldEditor;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

public final class SheetMusicPage extends MetadataPage<SheetMusic> {
    
    private FontIcon pdfIcon;
    private FontIcon musescoreIcon;
    
    /**
     * Builds a page to display the given item's info and possibly edit
     * their value
     *
     * @param item The item to display as a content
     */
    public SheetMusicPage(SheetMusic item) {
        super(item);
    }

    @Override
    protected void initComponent(VBox vBox) {
        

        EditableValue<String> editableLabel = FieldEditor.synchronize(
                new EditableLabel(Theme.Category.Accent, FontSize.DEFAULT_H1, false, Pos.CENTER),
                item::setName, item::getName
        );

        HBox filesHBox = new HBox();
        filesHBox.setAlignment(Pos.CENTER);

        pdfIcon = new ClickableFontIcon(MaterialDesignF.FILE_PDF_OUTLINE, ThemeManager.getWhiteColor(), FontSize.DEFAULT_BIG_ICON,
                () -> { if (item.getPdfFile() != null) { item.getPdfFile().open(); } });
        musescoreIcon = new ClickableFontIcon(MaterialDesignF.FILE_MUSIC_OUTLINE,ThemeManager.getWhiteColor(), FontSize.DEFAULT_BIG_ICON,
                () -> { if (item.getMusescoreFile() != null) { item.getMusescoreFile().open(); } });
        filesHBox.getChildren().addAll(pdfIcon, musescoreIcon);
        
        vBox.getChildren().addAll(editableLabel, filesHBox);
    }


}
