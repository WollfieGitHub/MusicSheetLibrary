package fr.wollfie.sheetmusiclibrary.io;

import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;

public final class MusicLibrary {

    /**
     * Sets the location of the music library database, which should be the root of the 
     * filesystem of sheet music which will be loaded
     * @param rootFile The location to set for the music library
     * @throws IllegalArgumentException if the path doesn't contain the word "sheet_music" to
     * make sure we aren't loading the library into the wrong folder
     * @throws IOException If there is a problem with the specified rootFile
     */
    public static void setLocation(File rootFile) throws IllegalArgumentException, IOException {
        // TODO
    }

    /**
     * Init a new music library at the set location or loads the existing one
     */
    public static void initOrLoad() throws IOException {
        
    }

    /**
     * Loads all the sheet music found from the root of the music library
     * @throws IOException if an error occurred during library load
     */
    public static void load() throws IOException {
        
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
