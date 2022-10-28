package fr.wollfie.sheetmusiclibrary.library;


import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

final class SheetSheetMusicLibraryTest {
    
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       INIT                                           ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @BeforeAll static void initLibrary() {
        assertDoesNotThrow(() -> {
            SheetMusicLibrary.setLocationAndInit(new File(LIBRARY_PATH));
        });
    }
    
    @BeforeEach void loadLibrary() {
        assertDoesNotThrow(SheetMusicLibrary::loadAll);
    }
    
    @AfterEach void saveLibrary() {
        assertDoesNotThrow(SheetMusicLibrary::save);
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       LOADING                                        ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void externalDiskForLocationIsSupported() {
        assertDoesNotThrow(() -> {
            SheetMusicLibrary.setLocationAndInit(new File("L:/music_library"));
        });
        initLibrary();
    }
    
    @Test void instrumentInsertionDoesntThrow() throws IOException {
        assertDoesNotThrow(() -> SheetMusicLibrary.insert(VALID_INSTRUMENT_1));
    }

    @Test void artistInsertionDoesntThrow() throws IOException {
        assertDoesNotThrow(() -> SheetMusicLibrary.insert(VALID_ARTIST_1));
    }
    
    @Test void validFileNameLoadsValidSheetMusicPdf() {
        SheetMusic sheetMusic = SheetMusicLibrary.findByName(VALID_SHEET.name());
        
        assertNotNull(sheetMusic);
        assertThat(sheetMusic.name(), is(VALID_SHEET.name()));
        assertNotNull(sheetMusic.pdfFile(), "Sheet's pdf is not null");
    }
    
    @Test void validFileNameLoadsValidSheetMusicMetadata() {
        SheetMusic sheetMusic = SheetMusicLibrary.findByName(VALID_SHEET.name());

        assertNotNull(sheetMusic);
        assertNotNull(sheetMusic.artistRef());
        assertThat(sheetMusic.artistRef().getValue().lastName(), is(VALID_SHEET.artistRef().getValue().lastName()));
    }
    
    @Test void validFileNameLoadsValidMusescoreFile() {
        SheetMusic sheetMusic = SheetMusicLibrary.findByName(VALID_SHEET.name());
        
        assertNotNull(sheetMusic);
        assertNotNull(sheetMusic.musescoreFile());
    }
    
    @Test void invalidFileNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> SheetMusicLibrary.findByName(INVALID_SHEET_NAME));
    }
    
}
