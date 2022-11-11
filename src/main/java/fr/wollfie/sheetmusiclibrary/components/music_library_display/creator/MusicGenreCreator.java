package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ColorPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.IntegerPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.StringPrompt;
import fr.wollfie.sheetmusiclibrary.dto.MusicGenre;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class MusicGenreCreator extends MetadataCreator<MusicGenre> {
    
    private final Property<String> nameProperty = new SimpleObjectProperty<>(null);
    private final Property<Color> colorProperty = new SimpleObjectProperty<>(null);
    private final Property<Integer> yearBeginProperty = new SimpleObjectProperty<>(null);
    private final Property<Integer> yearEndProperty = new SimpleObjectProperty<>(null);
    private final StringPrompt stringPrompt;

    public MusicGenreCreator(Consumer<MusicGenre> onMetadataCreated) {
        super(onMetadataCreated);

        IntegerPrompt yearEnd = new IntegerPrompt("Year End", onResultFinish(yearEndProperty));
        IntegerPrompt yearBegin = new IntegerPrompt("Year Begin", onResult(yearBeginProperty, yearEnd));
        ColorPrompt colorPrompt = new ColorPrompt("Genre Color", onResult(colorProperty, yearBegin));
        stringPrompt = new StringPrompt("Music Genre Name", onResult(nameProperty, colorPrompt));

        getChildren().addAll(stringPrompt);
    }

    @Override
    protected void onPreFinish() {
        
    }

    @Override
    protected <T> Function<T, MusicGenre> finalValueToResult() {
        return finalValue -> new MusicGenre(
                this.nameProperty.getValue(),
                Optional.of(this.yearBeginProperty.getValue()),
                Optional.of(this.yearEndProperty.getValue()),
                this.colorProperty.getValue()
        );
    }

    @Override
    public void mounted() {
        stringPrompt.getFocus();
    }
}
