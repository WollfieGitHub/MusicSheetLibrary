package fr.wollfie.sheetmusiclibrary.io;

import fr.wollfie.sheetmusiclibrary.MusicLibraryApplication;

import java.io.File;

public final class FileSystem {
    
    private FileSystem() {}
    
    public static final File BASE_PATH = new File(System.getenv("APPDATA"), MusicLibraryApplication.APP_NAME);
    static {
        BASE_PATH.mkdir();
    }
}
