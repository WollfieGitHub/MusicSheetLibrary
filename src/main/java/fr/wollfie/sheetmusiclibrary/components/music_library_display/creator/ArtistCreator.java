package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.*;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.beans.property.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ArtistCreator extends MetadataCreator<Artist> {
    
    private final Property<String> firstNameProperty = new SimpleObjectProperty<>(null);
    private final Property<Integer> yearOfBirthProperty = new SimpleObjectProperty<>(null);
    private final Property<Integer> yearOfDeathProperty = new SimpleObjectProperty<>(null);
    private final StringPrompt firstNamePrompt;

    public ArtistCreator(Consumer<Artist> onMetadataCreated) {
        super(onMetadataCreated);

        IntegerPrompt deathPrompt = new IntegerPrompt("Year of Death", onResultFinish(yearOfDeathProperty));
        IntegerPrompt birthPrompt = new IntegerPrompt("Year of Birth", onResult(yearOfBirthProperty, deathPrompt));
        firstNamePrompt = new StringPrompt("Name/Nickname", onResult(firstNameProperty, birthPrompt));

        getChildren().addAll(firstNamePrompt);
    }

    @Override
    protected void onPreFinish() { }

    @Override
    protected <T> Function<T, Artist> finalValueToResult() {
        return yofd -> {
            Artist artist = new Artist(
                    firstNameProperty.getValue(),
                    yearOfBirthProperty.getValue(),
                    (Integer) yofd
            );
            Logger.info(artist);
            return artist;
        };
    }

    @Override
    public void mounted() {
        firstNamePrompt.getFocus();
    }
}
