package fr.wollfie.sheetmusiclibrary.io;


import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.MusicGenre;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

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
            MusicLibrary.setLocation(new File(LIBRARY_PATH));
            MusicLibrary.initOrLoad();
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
            MusicLibrary.setLocation(new File("L:/"));
            MusicLibrary.load();
        });
        
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
