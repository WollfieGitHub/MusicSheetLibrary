package fr.wollfie.sheetmusiclibrary.dto;

import fr.wollfie.sheetmusiclibrary.components.display_adapters.*;

public enum MetadataType implements DisplayAdaptable {
    
    MusicCategory("Music Category", MusicCategory.class, new MusicCategoryDisplayAdapter()),
    MusicGenre("Music Genre", MusicGenre.class, new MusicGenreDisplayAdapter()),
    Instrument("Instrument", Instrument.class, new InstrumentDisplayAdapter()),
    Artist("Artist", Artist.class, new ArtistDisplayAdapter()),
    SheetMusic("Sheet Music", SheetMusic.class, new SheetMusicDisplayAdapter()),
        ;
    
    public final Class<?> metadataClass;
    public final String displayName;
    public final DisplayAdapter<?> displayAdapter;
    
    MetadataType(String displayName, Class<? extends MetadataObject> type, DisplayAdapter<? extends MetadataObject> displayAdapter) {
        this.displayName = displayName;
        this.metadataClass = type;
        this.displayAdapter = displayAdapter;
    }
    
    public static MetadataType fromClass(Class<? extends Metadata> type) {
        for (MetadataType type2 : values()) {
            if (type2.metadataClass.equals(type)) {
                return type2;
            }
        }
        throw new IllegalArgumentException("No such class found in types : \"" + type + "\"");
    }

    @Override
    public DisplayAdapter<?> getAdapter() {
        return this.displayAdapter;
    }
}
