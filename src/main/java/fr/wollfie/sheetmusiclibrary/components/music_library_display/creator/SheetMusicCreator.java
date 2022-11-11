package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.IntegerPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.MetadataPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.OptionalPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.StringPrompt;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.MusicCategory;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

public class SheetMusicCreator extends MetadataCreator<SheetMusic> {
    
    private final Property<String> nameProperty = new SimpleObjectProperty<>(null);
    private final Property<Artist> artistProperty = new SimpleObjectProperty<>(null);
    private final Property<MusicCategory> categoryProperty = new SimpleObjectProperty<>(null);
    
    private final StringPrompt namePrompt;
    
    public SheetMusicCreator(Consumer<SheetMusic> onMetadataCreated) {
        super(onMetadataCreated);

        MetadataPrompt<MusicCategory> categoryPrompt = new MetadataPrompt<>("Category", onResultFinish(categoryProperty), MusicCategory.class);
        MetadataPrompt<Artist> lastNamePrompt = new MetadataPrompt<>("Artist", onResult(artistProperty, categoryPrompt), Artist.class);
        namePrompt = new StringPrompt("Name", onResult(nameProperty, lastNamePrompt));

        getChildren().addAll(namePrompt);
    }

    @Override
    protected void onPreFinish() { }

    @Override
    protected <T> Function<T, SheetMusic> finalValueToResult() {
        return finalValueToResult -> new SheetMusic(
                    nameProperty.getValue(),
                    artistProperty.getValue(),
                    Collections.emptyList(),
                    categoryProperty.getValue(),
                    null, null
        );
    }

    @Override
    public void mounted() {
        namePrompt.getFocus();
    }
}
