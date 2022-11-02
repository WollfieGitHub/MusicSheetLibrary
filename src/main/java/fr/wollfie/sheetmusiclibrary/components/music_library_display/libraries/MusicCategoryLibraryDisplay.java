package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.ClickableLabel;
import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.List;

public class MusicCategoryLibraryDisplay extends MetadataItemDisplay<MusicCategory>  {
    
    public MusicCategoryLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected List<TableColumn<MusicCategory, ?>> initColumns() {
        TableColumn<MusicCategory, StackPane> iconTableColumn = new TableColumn<>("");
        iconTableColumn.setCellValueFactory(feature -> {
            MusicCategory category = feature.getValue();
            FontIcon icon = category.icon();
            icon.setIconColor(category.color());
            icon.setIconSize(FONT_SIZE);
            
            StackPane stackPane = new StackPane(icon);
            stackPane.setAlignment(Pos.BOTTOM_LEFT);
            return new SimpleObjectProperty<>(stackPane);
        });

        TableColumn<MusicCategory, StackPane> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            MusicCategory category = feature.getValue();
            Label label = new ClickableLabel(category.name());
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorHexFrom(null) + ";" +
                    "-fx-font-size: " + FONT_SIZE + ";");

            StackPane stackPane = new StackPane(label);
            stackPane.setAlignment(Pos.BOTTOM_LEFT);
            return new SimpleObjectProperty<>(stackPane);
        });
        
        return Arrays.asList(iconTableColumn, nameTableColumn);
    }

    @Override
    protected Class<MusicCategory> getContentClass() {
        return MusicCategory.class;
    }
}
