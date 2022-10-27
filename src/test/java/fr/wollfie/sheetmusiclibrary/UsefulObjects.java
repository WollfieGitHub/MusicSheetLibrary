package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.dto.MusicGenre;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class UsefulObjects {

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//

    public static final String LIBRARY_PATH = System.getProperty("user.dir") + "/src/test/resources";
    
    public static final Instrument VALID_INSTRUMENT = new Instrument("Clavichord", Color.ROSYBROWN, null);

    public static final MusicGenre VALID_GENRE = new MusicGenre(
            "Classique", Optional.of(1750), Optional.of(1830),
            Color.AQUA
    );
    public static final Artist VALID_ARTIST = new Artist(
            "Ludwig Van",
            "Beethoven",
            1778, Optional.of(1826),
            List.of(VALID_GENRE)
    );
    public static final SheetMusic VALID_SHEET = new SheetMusic(
            "Moonlight Sonata",
            VALID_ARTIST, VALID_INSTRUMENT, null, Optional.empty()
    );
    public static final String INVALID_SHEET_NAME = "Toonmight Sotana";
}
