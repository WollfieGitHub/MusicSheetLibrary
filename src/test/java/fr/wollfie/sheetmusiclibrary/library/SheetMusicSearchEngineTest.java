package fr.wollfie.sheetmusiclibrary.library;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

public class SheetMusicSearchEngineTest {
    
    @Test void noResultsReturnsWithNoResults() {
        List<Instrument> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.emptyList(), 255, 255
        );
        assertThat(instruments.size(), is(0));
    }
    
    @Test void oneResultReturnsWithOneResult() {
        List<Instrument> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.singletonList(VALID_INSTRUMENT_1), 255, 255
        );
        assertThat(instruments.size(), is(1));
    }

    @Test void oneResultReturnsWithNoResultIfDistanceRestricted() {
        List<Instrument> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.singletonList(VALID_INSTRUMENT_1), 255, 2
        );
        assertThat(instruments.size(), is(0));
    }

    @Test void oneResultReturnsWithNoResultIfNbItemRestricted() {
        List<Instrument> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.singletonList(VALID_INSTRUMENT_1), 0, 15
        );
        assertThat(instruments.size(), is(0));
    }

    @Test void twoResultsReturnsOrderedBest() {
        List<Instrument> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                VALID_INSTRUMENT_2.name(), Arrays.asList(VALID_INSTRUMENT_1, VALID_INSTRUMENT_2), 4, 15
        );
        assertThat(instruments.size(), is(2));
        assertThat(instruments.get(0).name(), is(VALID_INSTRUMENT_2.name()));
    }
    
    @Test void resultHasNoDuplicateAndIsOrderedForArtists() {
        List<Artist> instruments = SheetMusicSearchEngine.updatePropositionsAccordingTo(
                VALID_ARTIST_1.lastName(), Arrays.asList(VALID_ARTIST_1, VALID_ARTIST_1, SAME_ARTIST_2), 4, 255
        );
        // No duplicate
        assertThat(instruments.size(), is(2));
        assertThat(instruments.get(0).lastName(), is(VALID_ARTIST_1.lastName()));
    }
}
