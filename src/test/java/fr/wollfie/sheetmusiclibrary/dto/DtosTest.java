package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.number.OrderingComparison.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

public class DtosTest {
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       MUSIC CATEGORY                                 ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void musicCategoryHasNameAsSearchableString() {
        assertThat(new MusicCategory("Video Games", Color.AQUA).getSearchableTokenFields().size(),
                is(1));
    }
    
    @Test void musicCategoryHasNameAsEqualMethod() {
        assertThat(new MusicCategory("Tomato", Color.AQUA).equals(
                new MusicCategory("Tomato", Color.ROSYBROWN)
        ), is(true));

        assertThat(new MusicCategory("Tomato", Color.AQUA).equals(
                new MusicCategory("Tomatoa", Color.AQUA)
        ), is(false));

        assertThat(new MusicCategory("Tomato", Color.AQUA).equals(
                new Instrument("Tomato", Color.AQUA, null)
        ), is(false));
    }
    
    @Test void musicCategoryHasNameForUIdMethod() {
        assertThat(new MusicCategory("Tomato", Color.AQUA).getUId(),
                is(new MusicCategory("Tomato", Color.ROSYBROWN).getUId()));

        assertThat(new MusicCategory("Tomatoa", Color.AQUA).getUId(), 
                is(not(new MusicCategory("Tomato", Color.ROSYBROWN).getUId())));
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       ARTIST                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void artistHasNameAndYearsAsSearchableString() {
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.lastName()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.firstName()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(String.valueOf(VALID_ARTIST_1.yearOfBirth())), is(true));
    }
}
