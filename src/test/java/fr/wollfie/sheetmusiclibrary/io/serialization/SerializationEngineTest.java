package fr.wollfie.sheetmusiclibrary.io.serialization;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

final class SerializationEngineTest {
    
    private void saveArtist() throws IOException {
        SerializationEngine.saveTo(new File(LIBRARY_PATH + "/artist.json"), VALID_ARTIST);
    }
    
    private Artist loadArtist() throws IOException {
        return SerializationEngine.loadFrom(new File(LIBRARY_PATH + "/artist.json"), Artist.class);
    }
    
    private void saveInstrument() throws IOException {
        SerializationEngine.saveTo(new File(LIBRARY_PATH + "/instrument.json"), VALID_INSTRUMENT);
    }
    
    private Instrument loadInstrument() throws IOException {
        return SerializationEngine.loadFrom(new File(LIBRARY_PATH + "/instrument.json"), Instrument.class);
    }
    
    @Test void savingMetadataWithColorDoesntThrow() {
        assertDoesNotThrow(() -> saveInstrument());
    }
    
    @Test void savingMetadataWithOptionalDoesntThrow() {
        assertDoesNotThrow(this::saveArtist);
    }
    
    @Test void loadingMetadataWithOptionalDoesntThrow() {
        assertDoesNotThrow(this::loadArtist);
    }
    
    @Test void loadingMetadataWithOptionalPresentReturnValue() throws IOException {
        Artist artist = loadArtist();
        assertThat(artist.yearOfDeath().get(), is(VALID_ARTIST.yearOfDeath().get()));
    }
    
    @Test void loadingMetadataWithColorReturnsColor() throws IOException {
        Instrument instrument = loadInstrument();
        assertThat(instrument.color().getRed(), is(VALID_INSTRUMENT.color().getRed()));
    }
}
