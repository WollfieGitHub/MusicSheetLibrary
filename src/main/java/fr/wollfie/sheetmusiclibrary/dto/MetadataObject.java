package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

import java.util.List;
import java.util.UUID;

public abstract class MetadataObject implements Metadata {

    
    private String UId;
    @JsonProperty("UId") public void setUId(String UId) { this.UId = UId; }
    @JsonProperty("UId") public String getUId() { return UId; }

    /** Used by the rest of the classes to instantiate the metadata */
    public MetadataObject() {
        this.setUId(UUID.randomUUID().toString());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetadataObject metadataObject = (MetadataObject) o;

        return UId.equals(metadataObject.UId);
    }

    @Override
    public int hashCode() {
        return UId.hashCode();
    }
}
