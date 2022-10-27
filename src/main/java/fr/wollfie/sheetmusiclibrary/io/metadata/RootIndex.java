package fr.wollfie.sheetmusiclibrary.io.metadata;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * File for each folder in the library, used to speed up the search process
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RootIndex implements Metadata {

    private Map<String, String> metadataTypeToPath;

    public RootIndex(Map<String, String> metadataTypeToPath) {
        this.metadataTypeToPath = metadataTypeToPath;
    }
    
    public RootIndex() {}

    /**
     * Returns a root file with the given map registered
     * @param metadataTypeToPath The map of types to paths
     * @return A newly created index file
     */
    public static RootIndex initWith(Map<String, String> metadataTypeToPath) {
        return new RootIndex(metadataTypeToPath);
    }

    /**
     * @return a root file with an empty mapping
     */
    public static RootIndex initEmpty() {
        return new RootIndex(new HashMap<>());
    }

    /**
     * @param type The type of Metadata Object we want the path of
     * @return The path of the directory containing the specified metadata object type
     * @param <M> The type of the Metadata object
     */
    public <M extends Metadata> String getPathFrom(Class<M> type) {
        Preconditions.checkArgument(this.metadataTypeToPath.containsKey(type.getSimpleName()));
        
        return this.metadataTypeToPath.get(type.getSimpleName());
    }

    /**
     * Add an entry to the existing Root file
     * @param type The type of metadata object we want to register a mapping for
     * @param folder The directory in which the metadata object type is stored
     * @return The modified root file with the new entry aded
     * @param <M> The type of metadata
     */
    public <M extends Metadata> RootIndex addEntry(Class<M> type, File folder) {
        Preconditions.checkArgument(folder.isDirectory());
        
        Map<String, String> modifiedPaths = new HashMap<>(this.metadataTypeToPath);
        modifiedPaths.put(type.getSimpleName(), folder.getAbsolutePath());
        
        return new RootIndex(modifiedPaths);
    }
}
