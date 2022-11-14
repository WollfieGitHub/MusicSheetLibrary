package fr.wollfie.sheetmusiclibrary.dto.files;

import java.io.File;
import java.io.IOException;

public record MusescoreFile(
        File file
) implements ExecutableFile {
    
    @Override
    public void open() {
        try {
            new ProcessBuilder().command("start", file.getAbsolutePath()).start();
            
        } catch (IOException e) { throw new RuntimeException(e); }
    }
    
}
