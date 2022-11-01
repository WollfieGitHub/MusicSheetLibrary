package fr.wollfie.sheetmusiclibrary.dto;

import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collections;
import java.util.List;

public enum Clef implements Metadata {
    TREBLE("Treble", new FontIcon("mdi2m-music-clef-bass")),
    ALTO("Alto", new FontIcon("mdi2m-music-clef-alto")),
    BASS("Bass", new FontIcon("mdi2m-music-clef-treble"));

    public final String displayName;
    public final FontIcon fontIcon;
    
    Clef(String displayName, FontIcon icon) {
        this.displayName = displayName;
        this.fontIcon = icon;
    }

    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(displayName);
    }
}
