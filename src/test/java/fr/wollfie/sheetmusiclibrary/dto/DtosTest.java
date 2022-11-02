package fr.wollfie.sheetmusiclibrary.dto;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

public class DtosTest {
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       MUSIC CATEGORY                                 ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void musicCategoryHasNameAsSearchableString() {
        assertThat(new MusicCategory("Video Games", Color.AQUA, null).getSearchableTokenFields().size(),
                is(1));
    }
    
    @Test void musicCategoryHasNameAsEqualMethod() {
        assertThat(new MusicCategory("Tomato", Color.AQUA, null).equals(
                new MusicCategory("Tomato", Color.ROSYBROWN, null)
        ), is(true));

        assertThat(new MusicCategory("Tomato", Color.AQUA, null).equals(
                new MusicCategory("Tomatoa", Color.AQUA, null)
        ), is(false));

        assertThat(new MusicCategory("Tomato", Color.AQUA, null).equals(
                new Instrument("Tomato", Color.AQUA, null)
        ), is(false));
    }
    
    @Test void musicCategoryHasNameForUIdMethod() {
        assertThat(new MusicCategory("Tomato", Color.AQUA, null).getUId(),
                is(new MusicCategory("Tomato", Color.ROSYBROWN, null).getUId()));

        assertThat(new MusicCategory("Tomatoa", Color.AQUA, null).getUId(), 
                is(not(new MusicCategory("Tomato", Color.ROSYBROWN, null).getUId())));
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       ARTIST                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void artistHasNameAndYearsAsSearchableString() {
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.lastName().get()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.firstNameOrNickname()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(String.valueOf(VALID_ARTIST_1.yearOfBirth())), is(true));
    }
}
