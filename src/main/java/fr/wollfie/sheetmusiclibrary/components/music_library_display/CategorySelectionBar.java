package fr.wollfie.sheetmusiclibrary.components.music_library_display;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.controllers.ToggleThemedButton;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
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
    
    private final ObjectProperty<MetadataLibraryDisplay<?>> selectedCategory = new SimpleObjectProperty<>();
    private final List<Tuple<ToggleThemedButton, MetadataLibraryDisplay<?>>> buttons;
    private int selectedIndex = 0;

    public ReadOnlyObjectProperty<MetadataLibraryDisplay<?>> selectedCategoryProperty() {
        return selectedCategory;
    }

    public CategorySelectionBar(List<MetadataLibraryDisplay<?>> libraryDisplays) {

        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(5));
        setSpacing(5);

        buttons = new ArrayList<>();

        for (int i = 0; i < libraryDisplays.size(); i++) {
            final int index = i;
            MetadataLibraryDisplay<?> type = libraryDisplays.get(i);
            String name = type.getContentType().displayName;

            ToggleThemedButton toggleThemedButton = new ToggleThemedButton(name, null, Theme.Category.Primary);
            toggleThemedButton.setOnAction(event -> select(index));

            buttons.add(Tuple.of(toggleThemedButton, type));
            getChildren().add(toggleThemedButton);
        }
        
        select(0);
    }

    public void selectNext() {
        select((this.selectedIndex+1) % buttons.size());
    }
    
    private void select(int index) {
        this.selectedIndex = index;
        Tuple<ToggleThemedButton, MetadataLibraryDisplay<?>> selected = buttons.get(index);
        currentSelectedType = selected.right().getContentType();
        selectedCategory.set(selected.right());
        buttons.forEach(button -> button.left().setSelected(false));
        selected.left().setSelected(true);
    }
}
