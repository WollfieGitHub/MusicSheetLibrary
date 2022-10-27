package fr.wollfie.sheetmusiclibrary.io.metadata;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;

import java.io.IOException;

/**
 * Metadata file for each Sheet Music Library
 */
public class MetadataFile {

    private MetadataFile() {}
    
    /**
     * Load a metadata file from a relative path and a type
     * @param relativePath The path for the metadata file
     * @return The metadata object found in the file 
     * @param <R> The type of the metadata to be cast
     * @throws IOException if an error made the load impossible
     */
    public static <R extends Metadata> R loadFom(String relativePath) throws IOException {
        // TODO
        return null;
    }

    /**
     * Save a metadata object using a relative path to a file
     * @param relativePath The relative path to save the object to
     * @param metadataObject The object to save
     * @param <R> The type of the object to save
     * @throws IOException if an error made the save impossible
     */
    public static <R extends Metadata> void saveTo(String relativePath, R metadataObject) throws IOException {
        // po
    }
}
