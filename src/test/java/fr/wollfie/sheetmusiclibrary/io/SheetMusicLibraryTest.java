package fr.wollfie.sheetmusiclibrary.io;


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

final class SheetMusicLibraryTest {
    
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       INIT                                           ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @BeforeAll static void initLibrary() {
        assertDoesNotThrow(() -> {
            MusicLibrary.setLocationAndInit(new File(LIBRARY_PATH));
        });
    }
    
    @BeforeEach void loadLibrary() {
        assertDoesNotThrow(MusicLibrary::load);
    }
    
    @AfterEach void saveLibrary() {
        assertDoesNotThrow(MusicLibrary::save);
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       LOADING                                        ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @Test void externalDiskForLocationIsSupported() {
        assertDoesNotThrow(() -> {
            MusicLibrary.setLocationAndInit(new File("L:/music_library"));
        });
        initLibrary();
    }
    
    @Test void instrumentInsertionDoesntThrow() throws IOException {
        assertDoesNotThrow(() -> MusicLibrary.insert(VALID_INSTRUMENT));
    }

    @Test void artistInsertionDoesntThrow() throws IOException {
        assertDoesNotThrow(() -> MusicLibrary.insert(VALID_ARTIST));
    }
    
    @Test void validFileNameLoadsValidSheetMusicPdf() {
        SheetMusic sheetMusic = MusicLibrary.findByName(VALID_SHEET.name());
        
        assertNotNull(sheetMusic);
        assertThat(sheetMusic.name(), is(VALID_SHEET.name()));
        assertNotNull(sheetMusic.pdfFile(), "Sheet's pdf is not null");
    }
    
    @Test void validFileNameLoadsValidSheetMusicMetadata() {
        SheetMusic sheetMusic = MusicLibrary.findByName(VALID_SHEET.name());

        assertNotNull(sheetMusic);
        assertNotNull(sheetMusic.artist());
        assertThat(sheetMusic.artist().lastName(), is(VALID_SHEET.artist().lastName()));
    }
    
    @Test void validFileNameLoadsValidMusescoreFile() {
        SheetMusic sheetMusic = MusicLibrary.findByName(VALID_SHEET.name());
        
        assertNotNull(sheetMusic);
        assertFalse(sheetMusic.musescoreFile().isEmpty());
        assertNotNull(sheetMusic.musescoreFile().get());
    }
    
    @Test void invalidFileNameThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> { MusicLibrary.findByName(INVALID_SHEET_NAME); });
    }
    
}
