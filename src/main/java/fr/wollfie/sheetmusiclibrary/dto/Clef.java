package fr.wollfie.sheetmusiclibrary.dto;

import java.util.Collections;
import java.util.List;

public enum Clef implements Metadata {
    TREBLE("Treble"),
    ALTO("Alto"),
    BASS("Bass");

    public final String displayName;
    
    Clef(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(displayName);
    }
}
