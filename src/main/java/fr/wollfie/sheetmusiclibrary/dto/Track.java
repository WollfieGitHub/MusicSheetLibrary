package fr.wollfie.sheetmusiclibrary.dto;

import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

public record Track(
        MetadataRef<Instrument> instrument,
        Clef clef
) implements JsonSerializable { 
    
    public static Track with(Instrument instrument, Clef clef) {
        return new Track(new MetadataRef<>(instrument), clef);
    }
    
}
