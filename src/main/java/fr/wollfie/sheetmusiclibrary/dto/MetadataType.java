package fr.wollfie.sheetmusiclibrary.dto;

public enum MetadataType {
    Artist("Artist", Artist.class),
    Instrument("Instrument", Instrument.class),
    MusicGenre("Music Genre", MusicGenre.class),
    SheetMusic("Sheet Music", SheetMusic.class),
    MusicCategory("Music Category", MusicCategory.class),
        ;
    
    public final Class<?> metadataClass;
    public final String displayName;
    
    MetadataType(String displayName, Class<? extends Metadata> type) {
        this.displayName = displayName;
        this.metadataClass = type;
    }
    
    public static MetadataType fromClass(Class<? extends Metadata> type) {
        for (MetadataType type2 : values()) {
            if (type2.metadataClass.equals(type)) {
                return type2;
            }
        }
        throw new IllegalArgumentException("No such class found in types : \"" + type + "\"");
    }
}
