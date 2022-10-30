package fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.List;

public class InstrumentLibraryDisplay extends MetadataItemDisplay<Instrument>  {
    
    public InstrumentLibraryDisplay(SearchBar searchBar) {
        super(searchBar);
    }

    @Override
    protected Class<Instrument> getContentClass() {
        return Instrument.class;
    }

    @Override
    protected List<TableColumn<Instrument, ?>> initColumns() {
        TableColumn<Instrument, FontIcon> iconTableColumn = new TableColumn<>("");
        iconTableColumn.setCellValueFactory(feature -> {
            Instrument instrument = feature.getValue();
            FontIcon icon = instrument.icon();
            icon.setIconColor(instrument.color());
            icon.setIconSize(LibraryConstants.FONT_SIZE);
            return new SimpleObjectProperty<>(icon);
        });

        TableColumn<Instrument, Label> nameTableColumn = new TableColumn<>("");
        nameTableColumn.setCellValueFactory(feature -> {
            Instrument instrument = feature.getValue();
            Label label = new Label(instrument.name());
            label.setStyle("-fx-text-fill: " + ThemeManager.getTextColorFrom(null));
            return new SimpleObjectProperty<>(label);
        });
        
        return Arrays.asList(iconTableColumn, nameTableColumn);
    }
    
}
