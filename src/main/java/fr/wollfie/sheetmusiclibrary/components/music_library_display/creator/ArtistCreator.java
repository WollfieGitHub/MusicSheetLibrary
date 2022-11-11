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
    private final Property<Optional<String>> lastNameProperty = new SimpleObjectProperty<>(Optional.empty());
    private final Property<Integer> yearOfBirthProperty = new SimpleObjectProperty<>(null);
    private final Property<Optional<Integer>> yearOfDeathProperty = new SimpleObjectProperty<>(Optional.empty());
    private final StringPrompt firstNamePrompt;

    public ArtistCreator(Consumer<Artist> onMetadataCreated) {
        super(onMetadataCreated);

        OptionalPrompt<Integer> deathPrompt = new OptionalPrompt<Integer>("Year of Death", onResultFinish(yearOfDeathProperty), IntegerPrompt.class);
        IntegerPrompt birthPrompt = new IntegerPrompt("Year of Birth", onResult(yearOfBirthProperty, deathPrompt));
        OptionalPrompt<String> lastNamePrompt = new OptionalPrompt<>("Last Name", onResult(lastNameProperty, birthPrompt), StringPrompt.class);
        firstNamePrompt = new StringPrompt("First Name/Nickname", onResult(firstNameProperty, lastNamePrompt));

        getChildren().addAll(firstNamePrompt);
    }

    @Override
    protected void onPreFinish() { }

    @Override
    protected <T> Function<T, Artist> finalValueToResult() {
        return yofd -> {
            Artist artist = new Artist(
                    firstNameProperty.getValue(),
                    lastNameProperty.getValue(),
                    yearOfBirthProperty.getValue(),
                    (Optional<Integer>) yofd
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
