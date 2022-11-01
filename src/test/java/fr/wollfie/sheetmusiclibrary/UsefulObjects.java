package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class UsefulObjects {

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//

    @BeforeAll static void initDirectories() {
        new File(TEST_PATH).mkdir();
    }
    
    public static final String LIBRARY_PATH = System.getProperty("user.dir") + "/src/test/resources";
    public static final String TEST_PATH = System.getProperty("user.dir") + "/src/test/resources/tests";
    
    public static final Instrument VALID_INSTRUMENT_1 = new Instrument("Clavichord", Color.ROSYBROWN, new FontIcon("fas-window-minimize"));
    public static final Instrument VALID_INSTRUMENT_2 = new Instrument("Cello", Color.ROSYBROWN, null);

    public static final MusicGenre VALID_GENRE = new MusicGenre(
            "Classique", Optional.of(1750), Optional.of(1830),
            Color.AQUA
    );
    public static final Artist VALID_ARTIST_1 = new Artist("Ludwig Van", "Beethoven", 1778, Optional.of(1826), VALID_GENRE);
    public static final Artist SAME_ARTIST_1 = new Artist("Ludwig Van", "Theeloten", 1778, Optional.of(1826), VALID_GENRE);
    public static final Artist SAME_ARTIST_2 = new Artist("Ludwig Van", "Peebonen", 1778, Optional.of(1826), VALID_GENRE);
    
    public static final SheetMusic VALID_SHEET = new SheetMusic(
            "Moonlight Sonata",
            VALID_ARTIST_1,
            List.of(Track.with(VALID_INSTRUMENT_1, Clef.BASS)),
            null,
            null, null
    );
    public static final String INVALID_SHEET_NAME = "Toonmight Sotana";
}
