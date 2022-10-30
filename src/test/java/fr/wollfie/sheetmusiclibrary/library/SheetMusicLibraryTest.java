package fr.wollfie.sheetmusiclibrary.library;


import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    
    @Test void instrumentInsertionDoesntThrow() {
        assertDoesNotThrow(() -> SheetMusicLibrary.insert(VALID_INSTRUMENT_1));
    }

    @Test void artistInsertionDoesntThrow() {
        assertDoesNotThrow(() -> SheetMusicLibrary.insert(VALID_ARTIST_1));
    }
    
    @Test void validArtistIsFoundInDatabaseWithMinimalSearch() throws IOException {
        SheetMusicLibrary.insert(VALID_ARTIST_1);
        SheetMusicLibrary.insert(SAME_ARTIST_2);
        SheetMusicLibrary.insert(SAME_ARTIST_1);
        List<Artist> results = SheetMusicLibrary.searchFor("Beethoven", MetadataType.Artist);
        
        assertNotNull(results);
        assertThat(results.isEmpty(), is(false));
        assertThat(results.get(0).lastName(), is(VALID_ARTIST_1.lastName()));
    }
    
    @Test void validFileNameLoadsValidSheetMusicMetadata() {

    }
    
    @Test void validFileNameLoadsValidMusescoreFile() {
        
    }
    
    @Test void invalidFileNameThrows() {
        
    }
    
}
