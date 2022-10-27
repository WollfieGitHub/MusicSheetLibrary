package fr.wollfie.sheetmusiclibrary.io;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.io.metadata.RootIndex;
import fr.wollfie.sheetmusiclibrary.io.serialization.SerializationEngine;

import java.io.File;
import java.io.IOException;

public final class MusicLibrary {

    private static RootIndex rootIndex;
    private static File rootFileObject;
    
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
}
