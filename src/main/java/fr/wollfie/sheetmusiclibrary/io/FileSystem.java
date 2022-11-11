package fr.wollfie.sheetmusiclibrary.io;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.MusicLibraryApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public final class FileSystem {
    
    private FileSystem() {}
    
    public static final File BASE_PATH = new File(System.getenv("APPDATA"), MusicLibraryApplication.APP_NAME);
    static {
        BASE_PATH.mkdir();
    }
    
    public static void openFile(File file) {
        try {
            new ProcessBuilder(file.getAbsolutePath()).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Optional<File> getFirstFileWith(String extension, File inDirectory) {
        File[] files;
        if (inDirectory.isDirectory() && (files = inDirectory.listFiles()) != null) {
            return Arrays.stream(files).parallel()
                    .map(file -> getFirstFileWith(extension, file))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();
        } else {
            if (inDirectory.getName().endsWith(extension)) { return Optional.of(inDirectory); }
            if (inDirectory.getName().endsWith(extension.toUpperCase())) { return Optional.of(inDirectory); }
            else { return Optional.empty(); }
        }
    }
    
    public static boolean deleteDirectory(File directory) {

        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }
}
