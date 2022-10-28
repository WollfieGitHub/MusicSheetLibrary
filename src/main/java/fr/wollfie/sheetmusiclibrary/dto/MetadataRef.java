package fr.wollfie.sheetmusiclibrary.dto;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;

/**
 * Reference to an existing metadata object, the latter object can be unloaded
 * @param <M> The type of metadata object to store
 */
public class MetadataRef<M extends Metadata> {
    
    private M value;
    public final String valueUId;

    /**
     * @implNote Calling this method will resolve the reference and load the object if it
     * wasn't previously loaded
     * @return the metadata object this reference is pointing to
     */
    public M getValue() {
        if (value == null) {
            value = SheetMusicLibrary.resolve(this);
        }
        return value;
    }

    /**
     * Creates a new metadata reference to the given metadata object
     * @param value The value to point to
     */
    public MetadataRef(M value) {
        Preconditions.checkNotNull(value);
        
        this.value = value;
        this.valueUId = value.getUId();
    }

    /**
     * Creates a new metadata reference to the metadata object link with the given uid
     * @param valueUId The uid of the object the reference is pointing to
     */
    public MetadataRef(String valueUId) {
        Preconditions.checkNotNull(valueUId);
        
        this.value = null;
        this.valueUId = valueUId;
    }
}
