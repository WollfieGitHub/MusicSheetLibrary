package fr.wollfie.sheetmusiclibrary.io.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Clef;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;

final class SerializationEngineTest {
    
    private void saveArtist() throws IOException {
        SerializationEngine.saveTo(new File(LIBRARY_PATH + "/artist.json"), VALID_ARTIST_1);
    }
    
    private Artist loadArtist() throws IOException {
        return SerializationEngine.loadFrom(new File(LIBRARY_PATH + "/artist.json"), Artist.class);
    }
    
    private void saveInstrument() throws IOException {
        SerializationEngine.saveTo(new File(LIBRARY_PATH + "/instrument.json"), VALID_INSTRUMENT_1);
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
    
    @Test void serializeTupleDoesNotThrow() {
        Tuple<Instrument, Clef> tuple = new Tuple<>(VALID_INSTRUMENT_2, Clef.BASS);
        assertDoesNotThrow(() -> SerializationEngine.saveTo(new File(TEST_PATH, "tuple.json"), tuple));
    }
    
    @Test void deserializedTupleIsIdenticalToSerialized() {
        File file = new File(TEST_PATH, "tuple.json");
        Tuple<Instrument, Clef> tuple = new Tuple<>(VALID_INSTRUMENT_2, Clef.BASS);
        assertDoesNotThrow(() -> SerializationEngine.saveTo(file, tuple));
        
        AtomicReference<Tuple<Instrument, Clef>> recoveredTuple = new AtomicReference<>();
        assertDoesNotThrow(() -> recoveredTuple.set(SerializationEngine.loadFrom(file, new TypeReference<>() {})));
        assertThat(recoveredTuple.get(), is(tuple));
    }
    
    @Test void loadingMetadataWithOptionalPresentReturnValue() throws IOException {
        assertDoesNotThrow(this::saveArtist);
        
        Artist artist = loadArtist();
        assertThat(artist.getYearOfDeath(), is(VALID_ARTIST_1.getYearOfDeath()));
    }
    
    @Test void loadingMetadataWithColorReturnsColor() throws IOException {
        SerializationEngine.saveTo(new File(LIBRARY_PATH + "/instrument.json"), VALID_INSTRUMENT_1);
        
        Instrument instrument = loadInstrument();
        assertThat(instrument.getColor().getRed(), is(VALID_INSTRUMENT_1.getColor().getRed()));
    }
}
