package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ColorPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.FontIconPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.StringPrompt;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;
import java.util.function.Function;

public class MusicCategoryCreator extends MetadataCreator<MusicCategory> {

    private final StringProperty nameProperty = new SimpleStringProperty(null);
    private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<FontIcon> iconProperty = new SimpleObjectProperty<>(null);
    private final StringPrompt stringPrompt;

    public MusicCategoryCreator(Consumer<MusicCategory> onMetadataCreated) {
        super(onMetadataCreated);

        ColorPrompt colorPrompt = new ColorPrompt("Icon Color", onResultFinish(colorProperty));
        FontIconPrompt iconChoice = new FontIconPrompt("Category Icon", onResult(iconProperty, colorPrompt));
        stringPrompt = new StringPrompt("Category Name", onResult(nameProperty, iconChoice));

        getChildren().addAll(stringPrompt);
    }
    @Override
    protected void onPreFinish() {
        this.iconProperty.get().setIconColor(this.colorProperty.get());
    }

    @Override
    protected <T> Function<T, MusicCategory> finalValueToResult() {
        return finalResult -> new MusicCategory(
                this.nameProperty.get(),
                this.colorProperty.get(),
                this.iconProperty.get()
        );
    }

    @Override
    public void mounted() {
        stringPrompt.getFocus();
    }
}
