package fr.wollfie.sheetmusiclibrary.io.metadata;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;
import fr.wollfie.sheetmusiclibrary.io.serialization.SerializationEngine;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Index file containing a bunch of Metadata objects
 * @param <M> The type of metadata the index contains
 */
public class MetadataIndex<M extends Metadata> {
    
    private final File file;
    public final ObservableList<M> metadata = new SimpleListProperty<>(FXCollections.observableList(
            new ArrayList<>()
    ));
    private final Map<String, M> metadataById = new HashMap<>();

    private MetadataIndex(List<M> metadata, File file) {
        this.metadata.addAll(metadata);
        this.file = file;
        reloadIndices();
    }

    /**
     * Creates a new index with already existing metadata using a file
     * @param metadata The metadata objects to include in the index at init
     * @param file The file to use for serialization of the index
     * @return The newly created index
     * @param <M> The type of metadata the index contains
     */
    public static <M extends Metadata> MetadataIndex<M> createFrom(List<M> metadata, File file) {
        Preconditions.checkNotNull(metadata);
        return new MetadataIndex<>(metadata, file);
    }

    /**
     * Add a new metadata object into the index
     * @implNote This call saves the file again with the newly added object and reload the indices
     * @param metadata The metadata object to insert
     * @throws IOException if an error occurred during insertion or save
     */
    public void add(M metadata) throws IOException {
        this.metadata.add(metadata);
        saveAll();
        reloadIndices();
    }

    private void reloadIndices() {
        metadataById.clear();
        metadata.parallelStream()
                .forEach(m -> metadataById.put(m.getUId(), m));
    }

    /**
     * Save the indices into its associated file
     * @throws IOException if an error occurred
     */
    public void saveAll() throws IOException {
        SerializationEngine.saveAllTo(file, this.metadata);
    }

    /**
     * Reload all the objects data from the associate file and returns this object
     */
    public MetadataIndex<M> reload() {
        // TODO
        
        reloadIndices();
        return this;
    }

    public M getFromRef(MetadataRef<M> ref) {
        Preconditions.checkArgument(this.metadataById.containsKey(ref.valueUId));
        return this.metadataById.get(ref.valueUId);
    }
}
