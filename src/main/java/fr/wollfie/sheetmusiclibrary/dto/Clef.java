package fr.wollfie.sheetmusiclibrary.dto;

import org.kordamp.ikonli.javafx.FontIcon;

import java.util.*;

public enum Clef implements Metadata {
    TREBLE("G", "Treble", new FontIcon("mdi2m-music-clef-treble")),
    ALTO("C", "Alto", new FontIcon("mdi2m-music-clef-alto")),
    BASS("F", "Bass", new FontIcon("mdi2m-music-clef-bass"));

    
    public final String englishNoteName;
    public final String displayName;
    public final FontIcon fontIcon;
    
    Clef(String englishNoteName, String displayName, FontIcon icon) {
        this.englishNoteName = englishNoteName;
        this.displayName = displayName;
        this.fontIcon = icon;
    }

    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(displayName);
    }

    public static Clef fromEnglishName(String displayName) {
        return Arrays.stream(values()).filter(clef -> clef.englishNoteName.equals(displayName))
                .findFirst()
                .orElse(null);
    }
}