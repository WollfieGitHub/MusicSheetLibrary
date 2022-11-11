package fr.wollfie.sheetmusiclibrary.library;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.LingualString;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.number.OrderingComparison.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

public class SearchEngineTest {
    
    @Test void noResultsReturnsWithNoResults() {
        List<Instrument> instruments = SearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.emptyList(), 255
        );
        assertThat(instruments.size(), is(0));
    }
    
    @Test void oneResultReturnsWithOneResult() {
        List<Instrument> instruments = SearchEngine.updatePropositionsAccordingTo(
                VALID_INSTRUMENT_1.getName().getEnglishTranslation(), Collections.singletonList(VALID_INSTRUMENT_1), 255
        );
        assertThat(instruments.size(), is(1));
    }
    
    @Test void oneResultReturnsWithNoResultIfNbItemRestricted() {
        List<Instrument> instruments = SearchEngine.updatePropositionsAccordingTo(
                "coconut", Collections.singletonList(VALID_INSTRUMENT_1), 0
        );
        assertThat(instruments.size(), is(0));
    }

    @Test void twoResultsReturnsOrderedBest() {
        List<Instrument> instruments = SearchEngine.updatePropositionsAccordingTo(
                "Cl", Arrays.asList(VALID_INSTRUMENT_1, VALID_INSTRUMENT_2), 4
        );
        assertThat(instruments.size(), is(2));
        assertThat(instruments.get(0).getName(), is(VALID_INSTRUMENT_2.getName()));
    }
    
    @Test void resultHasNoDuplicateAndIsOrderedForArtists() {
        List<Artist> instruments = SearchEngine.updatePropositionsAccordingTo(
                VALID_ARTIST_1.getLastName().get(), Arrays.asList(VALID_ARTIST_1, VALID_ARTIST_1, SAME_ARTIST_2), 4
        );
        // No duplicate
        assertThat(instruments.size(), is(1));
        assertThat(instruments.get(0).getLastName(), is(VALID_ARTIST_1.getLastName()));
    }
    
    @Test void speedTest() {
        int n = 50_000;
        double criterion = 1 / 25.0;
        long timeMs = 0;
        List<Instrument> instruments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            instruments.add(new Instrument(new LingualString(UUID.randomUUID().toString()), Color.BROWN, null));
        }
        long initialMs = System.currentTimeMillis();
        SearchEngine.updatePropositionsAccordingTo("coconut", instruments, n+1);
        timeMs = System.currentTimeMillis() - initialMs;
        Logger.infof("Execution time : %dms", timeMs);
        
        assertThat(timeMs, is(lessThan((long)(n * criterion))));
    }
}
