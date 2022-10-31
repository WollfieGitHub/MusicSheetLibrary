package fr.wollfie.sheetmusiclibrary.library;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.io.metadata.MetadataIndex;
import fr.wollfie.sheetmusiclibrary.io.network.ArtistImageRetriever;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SheetMusicLibrary {
    
    public static final File DEFAULT_LOCATION = new File(System.getProperty("user.home") + File.separator + "SheetMusicLibrary");
    
    private static File rootFileObject;
    private static String baseDirectory;
    
    private static final ListProperty<SheetMusic> sheetMusics
            = new SimpleListProperty<>(FXCollections.observableList(Collections.emptyList()));

    public static ReadOnlyListProperty<SheetMusic> sheetMusicsProperty() {
        return sheetMusics;
    }

    private static final Map<String, MetadataIndex<?>> indices = new HashMap<>();
    
    /**
     * Sets the location of the music library database, which should be the root of the 
     * filesystem of sheet music which will be loaded
     * @param rootFileLocation The location to set for the music library
     * @throws IllegalArgumentException if the path doesn't contain the word "sheet_music" to
     * make sure we aren't loading the library into the wrong folder
     * @throws IOException If there is a problem with the specified rootFileLocation
     */
    public static void setLocationAndInit(File rootFileLocation) throws IllegalArgumentException, IOException {
        Preconditions.checkArgument(rootFileLocation.getParentFile().exists());
        Preconditions.checkArgument(rootFileLocation.getParentFile().isDirectory());
        
        rootFileLocation.mkdir();

        baseDirectory = rootFileLocation.getAbsolutePath();
        rootFileObject = new File(rootFileLocation, "music_library_root.json");
        
        Logger.infof("Location of the library set to \"%s\"", rootFileLocation.getAbsolutePath());
        
        loadAll();
    }

    /**
     * Loads all the sheet music found from the root of the music library
     */
    public static void loadAll() {
        createIndices();
        for (MetadataIndex<?> metadataIndex : indices.values()) {
            metadataIndex.reload();
        }
    }

    private static void createIndices() {
        

        for (MetadataType type : MetadataType.values()) {
            File directory = new File(baseDirectory, type.displayName);
            directory.mkdir();
            File indexFile = new File(directory,
                    type.displayName + "_index.json");
            
            indices.put(type.displayName, MetadataIndex.createFrom((Class<? extends Metadata>) type.metadataClass, indexFile));
        }
    }

    /**
     * Save all the changes until now
     */
    public static void save() throws IOException{
        for (MetadataIndex<?> index: indices.values()) {
            index.saveAll();
        }
    }

    /**
     * Finds a sheet music in the database by name
     * @param searchReference A string to search for a metadata of the specified type
     * @param category The category of metadata to search for
     * @param nbItems The number of items to return
     * @throws IllegalArgumentException when no sheet music is found with the given name
     * @return The {@link SheetMusic} corresponding to the given name or null if none is found
     */
    @SuppressWarnings("unchecked")
    public static <M extends Metadata> List<M> searchFor(String searchReference, MetadataType category,
                                                         int nbItems)
            throws IllegalArgumentException {
        return (List<M>) SearchEngine.updatePropositionsAccordingTo(
                searchReference,
                indices.get(category.displayName).metadata.stream().toList(),
                nbItems
        );
    }

    /**
     * Finds a sheet music in the database by name
     * @param searchReference A string to search for a metadata of the specified type
     * @param category The category of metadata to search for
     * @throws IllegalArgumentException when no sheet music is found with the given name
     * @return The {@link SheetMusic} corresponding to the given name or null if none is found
     */
    public static <M extends Metadata> List<M> searchFor(String searchReference, MetadataType category)
            throws IllegalArgumentException {
        return searchFor(searchReference, category, Integer.MAX_VALUE);
    }

    /**
     * Insert new metadata into the music library
     * @param metadata The metadata to insert
     */
    public static <M extends Metadata> void insert(M metadata) throws IOException {
        MetadataIndex<M> metadataIndex = (MetadataIndex<M>) indices.get(MetadataType.fromClass(metadata.getClass()).displayName);
        
        // Fetch an image for an artist before insertion
        if (metadata instanceof Artist artist && !artist.imageUrl().fetched) {
            metadata = (M)artist.withImage(LazyImageUrl.fromResult(ArtistImageRetriever.fetchFor(artist)));
        }
        metadataIndex.add(metadata);
    }

    /**
     * Resolves a reference to a Metadata object by finding the object if it is loaded
     * or loading it otherwise using its Uid
     * @param ref The reference to resolve, we must return its value
     * @return The value of the reference, a metadata object instance
     * @param <M> The type fo the metadata object
     */
    @SuppressWarnings("unchecked")
    public static <M extends Metadata> M resolve(MetadataRef<M> ref) {
        Logger.infof("Type found : %s", ref.type);
        try {
            return ((MetadataIndex<M>) indices.get(ref.type.displayName))
                    .reload()
                    .getFromRef(ref);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
