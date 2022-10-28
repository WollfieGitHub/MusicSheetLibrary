package fr.wollfie.sheetmusiclibrary.io;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.io.metadata.MetadataIndex;
import fr.wollfie.sheetmusiclibrary.io.metadata.RootIndex;
import fr.wollfie.sheetmusiclibrary.io.serialization.SerializationEngine;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MusicLibrary {

    private static RootIndex rootIndex;
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
        Preconditions.checkArgument(rootFileLocation.exists());
        Preconditions.checkArgument(rootFileLocation.isDirectory());

        baseDirectory = rootFileLocation.getAbsolutePath();
        rootFileLocation = new File(rootFileLocation, "music_library_root.json");
        
        Logger.infof("Location of the library set to \"%s\"", rootFileLocation.getAbsolutePath());
        rootFileObject = rootFileLocation;
        
        load();
    }

    /**
     * Loads all the sheet music found from the root of the music library
     * @throws IOException if an error occurred during library load
     */
    public static void load() throws IOException {
        if (rootFileObject.exists()) {
            rootIndex = SerializationEngine.loadFrom(rootFileObject, RootIndex.class);
        } else {
            rootIndex = RootIndex.initEmpty();
            SerializationEngine.saveTo(rootFileObject, rootIndex);
        }
        createIndices();
    }

    private static void createIndices() throws IOException {

        List<Class<? extends Metadata>> metadataTypes = List.of(
                Artist.class,
                Instrument.class,
                MusicCategory.class,
                MusicGenre.class
        );

        for (Class<? extends Metadata> metadataType: metadataTypes) {
            File directory = new File(baseDirectory, metadataType.getSimpleName());
            directory.mkdir();
            File indexFile = new File(directory, 
                    metadataType.getSimpleName() + "_index.json");
            rootIndex = rootIndex.addEntry(metadataType, indexFile);
            
            indices.put(metadataType.getSimpleName(), MetadataIndex.createFrom(
                    rootIndex.loadAll(metadataType),
                    indexFile
            ));
        }
    }

    /**
     * Save all the changes until now
     */
    public static void save() throws IOException{
        
    }

    /**
     * Finds a sheet music in the database by name
     * @param sheetName The name of the sheet music, corresponding to the {@link SheetMusic} getName() result
     * @throws IllegalArgumentException when no sheet music is found with the given name
     * @return The {@link SheetMusic} corresponding to the given name or null if none is found
     */
    public static SheetMusic findByName(String sheetName) throws IllegalArgumentException {
        return null;
    }

    /**
     * Insert new metadata into the music library
     * @param metadata The metadata to insert
     */
    public static <M extends Metadata> void insert(M metadata) throws IOException {
        MetadataIndex<M> metadataIndex = (MetadataIndex<M>) indices.get(metadata.getClass().getSimpleName());
        metadataIndex.add(metadata);
    }
}
