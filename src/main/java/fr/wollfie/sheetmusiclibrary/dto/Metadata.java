package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

import java.util.List;
import java.util.UUID;

public interface Metadata extends JsonSerializable {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    default String getUId() { return String.valueOf(this.hashCode()); }
    
    /**
     * @return The value of the fields as strings for which a search is relevant for the user
     */
    @JsonIgnore List<String> getSearchableTokenFields();
}
