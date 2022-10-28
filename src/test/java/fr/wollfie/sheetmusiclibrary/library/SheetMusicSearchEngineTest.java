package fr.wollfie.sheetmusiclibrary.library;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.scene.paint.Color;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.number.OrderingComparison.*;
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
    
    @Test void speedTest() {
        int n = 50_000;
        double criterion = 1 / 25.0;
        long timeMs = 0;
        List<Instrument> instruments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            instruments.add(new Instrument(UUID.randomUUID().toString(), Color.BROWN, null));
        }
        long initialMs = System.currentTimeMillis();
        SheetMusicSearchEngine.updatePropositionsAccordingTo("coconut", instruments, n+1, 255);
        timeMs = System.currentTimeMillis() - initialMs;
        Logger.infof("Execution time : %dms", timeMs);
        
        assertThat(timeMs, is(lessThan((long)(n * criterion))));
    }
}