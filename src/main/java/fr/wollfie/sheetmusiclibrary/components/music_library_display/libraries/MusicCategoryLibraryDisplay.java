package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.List;

public class MusicCategoryLibraryDisplay extends MetadataItemDisplay<MusicCategory>  {
    
    public MusicCategoryLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected List<TableColumn<MusicCategory, ?>> initColumns() {
        TableColumn<MusicCategory, FontIcon> iconTableColumn = new TableColumn<>("");
        iconTableColumn.setCellValueFactory(feature -> {
            MusicCategory category = feature.getValue();
            FontIcon icon = category.icon();
            icon.setIconColor(category.color());
            icon.setIconSize(FONT_SIZE);
            return new SimpleObjectProperty<>(icon);
        });

        TableColumn<MusicCategory, Label> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            MusicCategory category = feature.getValue();
            Label label = new Label(category.name());
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");
            return new SimpleObjectProperty<>(label);
        });
        
        return Arrays.asList(iconTableColumn, nameTableColumn);
    }

    @Override
    protected Class<MusicCategory> getContentClass() {
        return MusicCategory.class;
    }
}
