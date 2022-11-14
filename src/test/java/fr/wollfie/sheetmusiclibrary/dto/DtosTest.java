package fr.wollfie.sheetmusiclibrary.dto;

import fr.wollfie.sheetmusiclibrary.utils.LingualString;
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
    
    @Test void musicCategoryHasUniqueUIdForInstancesMethod() {
        assertThat(new MusicCategory("Tomato", Color.AQUA, null).getUId(),
                is(not(new MusicCategory("Tomato", Color.ROSYBROWN, null).getUId())));

        assertThat(new MusicCategory("Tomatoa", Color.AQUA, null).getUId(), 
                is(not(new MusicCategory("Tomato", Color.ROSYBROWN, null).getUId())));
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       ARTIST                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void artistHasNameAndYearsAsSearchableString() {
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.getLastName()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(VALID_ARTIST_1.getFirstNameOrNickname()), is(true));
        assertThat(VALID_ARTIST_1.getSearchableTokenFields().contains(String.valueOf(VALID_ARTIST_1.getYearOfBirth())), is(true));
    }
}
