package fr.wollfie.sheetmusiclibrary.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;


final class SheetMusicTest {
    
    @Test void sheetMusicReturnsCorrectFullName() {
        assertThat(VALID_SHEET.fullName(), is(VALID_SHEET.name() + " - " + VALID_SHEET.artistRef().getValue().lastName()));
    }
    
}
