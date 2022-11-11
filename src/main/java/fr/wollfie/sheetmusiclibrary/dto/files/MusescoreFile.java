package fr.wollfie.sheetmusiclibrary.dto.files;

import java.io.File;

public record MusescoreFile(
        File file
) implements ExecutableFile {
    @Override
    public void open() {
        
    }
}
