package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.libraries.MetadataItemDisplay;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.controllers.ToggleThemedButton;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectionBar extends HBox {
    
    public static MetadataType currentSelectedType;
    
    private final ObjectProperty<MetadataItemDisplay<?>> selectedCategory = new SimpleObjectProperty<>();

    public ReadOnlyObjectProperty<MetadataItemDisplay<?>> selectedCategoryProperty() {
        return selectedCategory;
    }

    public CategorySelectionBar(List<MetadataItemDisplay<?>> libraryDisplays) {

        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(5));
        setSpacing(5);

        List<ToggleThemedButton> buttons = new ArrayList<>();

        for (MetadataItemDisplay<?> type: libraryDisplays) {
            String name = type.getContentType().displayName;

            ToggleThemedButton toggleThemedButton = new ToggleThemedButton(name, null, Theme.Category.Primary);
            buttons.add(toggleThemedButton);
            toggleThemedButton.setOnAction(e -> {
                currentSelectedType = type.getContentType();
                selectedCategory.set(type);
                buttons.forEach(button -> button.setSelected(false));
                toggleThemedButton.setSelected(true);
            });
            getChildren().add(toggleThemedButton);
        }
        this.selectedCategory.set(libraryDisplays.get(0));
        buttons.get(0).setSelected(true);
    }
}
