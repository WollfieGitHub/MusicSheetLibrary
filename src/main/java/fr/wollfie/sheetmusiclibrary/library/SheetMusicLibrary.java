package fr.wollfie.sheetmusiclibrary.library;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.io.metadata.MetadataIndex;
import fr.wollfie.sheetmusiclibrary.io.network.ArtistImageRetriever;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class SheetMusicLibrary {
    
    public static final File DEFAULT_LOCATION = new File(System.getProperty("user.home") + File.separator + "SheetMusicLibrary");
    
    private static String baseDirectory;

    /**
     * @param mClass The class of a metadata
     * @return The observable list of all metadata of the specified type in the database
     * @param <M> The type of metadata
     */
    @SuppressWarnings("unchecked")
    public static <M extends MetadataObject> ObservableList<M> getMetadataListProperty(Class<M> mClass) {
        MetadataType type = MetadataType.fromClass(mClass);
        Preconditions.checkArgument(indices.containsKey(type.displayName));
        return (ObservableList<M>) indices.get(type.displayName).metadata;
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
            File indexFile = new File(directory, type.displayName + "_index.json");
            
            indices.put(type.displayName, MetadataIndex.createFrom((Class<? extends MetadataObject>) type.metadataClass, indexFile));
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
    public static <M extends MetadataObject> List<M> searchFor(String searchReference, MetadataType category,
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
     * @param nbItems The number of items to return
     * @throws IllegalArgumentException when no sheet music is found with the given name
     * @return The {@link SheetMusic} corresponding to the given name or null if none is found
     */
    @SuppressWarnings("unchecked")
    public static <M extends MetadataObject> Optional<Tuple<M, Double>> searchForMatch(String searchReference, MetadataType category,
                                                                        int nbItems)
            throws IllegalArgumentException {
        return Tuple.mapLeft(SearchEngine.updatePropositionsAccordingTo(
                searchReference,
                indices.get(category.displayName).metadata.stream().toList(),
                nbItems, 0.9
        ).stream(), left -> (M)left).findFirst();
    }
    
    /** @return All sheets music with the specified artist as one of the contributors */
    public static List<SheetMusic> getAllSheetMusicWith(Artist artist) {
        return indices.get(MetadataType.SheetMusic.displayName).metadata.parallelStream()
                .map(metadata -> (SheetMusic)metadata)
                .filter(sheet -> artist.getUId().equals(sheet.getArtistRef().valueUId))
                .toList();
    } 

    /**
     * Finds a sheet music in the database by name
     * @param searchReference A string to search for a metadata of the specified type
     * @param category The category of metadata to search for
     * @throws IllegalArgumentException when no sheet music is found with the given name
     * @return The {@link SheetMusic} corresponding to the given name or null if none is found
     */
    public static <M extends MetadataObject> List<M> searchFor(String searchReference, MetadataType category)
            throws IllegalArgumentException {
        return searchFor(searchReference, category, Integer.MAX_VALUE);
    }

    /**
     * Insert new metadata into the music library
     * @param metadata The metadata to insert
     */
    public static <M extends MetadataObject> void insert(M metadata) throws IOException {
        MetadataIndex<M> metadataIndex = (MetadataIndex<M>) indices.get(MetadataType.fromClass(metadata.getClass()).displayName);
        if (metadataIndex.metadata.contains(metadata)) { 
            Logger.warningf("You tried to insert \"%s\" but was already present in the library !", metadata);
            return;
        }
        
        // Fetch an image for an artist before insertion
        metadataIndex.add(metadata);
        Logger.infof("%s was successfully inserted.", metadata);
    }

    /**
     * Same as {@link SheetMusicLibrary#insert(MetadataObject)} but returns false if 
     * an exception is raised
     * @param metadata The metadata to insert 
     * @return False if there was a problem during insertion of the item
     * @param <M> The type of metadata to insert
     */
    public static <M extends MetadataObject> boolean tryInsert(M metadata) {
        try {
            insert(metadata);
            return true;
        } catch (IOException e) { return false; }
    } 

    /**
     * Resolves a reference to a Metadata object by finding the object if it is loaded
     * or loading it otherwise using its Uid
     * @param ref The reference to resolve, we must return its value
     * @return The value of the reference, a metadata object instance
     * @param <M> The type fo the metadata object
     */
    @SuppressWarnings("unchecked")
    public static <M extends MetadataObject> M resolve(MetadataRef<M> ref) {
        try {
            return ((MetadataIndex<M>) indices.get(ref.type.displayName)).getFromRef(ref);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
