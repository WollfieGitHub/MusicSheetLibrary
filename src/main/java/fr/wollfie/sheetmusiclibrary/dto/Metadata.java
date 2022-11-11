package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

import java.util.List;

public interface Metadata extends JsonSerializable {

    /**
     * @return The value of the fields as strings for which a search is relevant for the user
     */
    @JsonIgnore
    List<String> getSearchableTokenFields();

    /** @return The type enum of metadata corresponding to this instance */
    @JsonIgnore
    default MetadataType getType() { return MetadataType.fromClass(this.getClass()); }

}
