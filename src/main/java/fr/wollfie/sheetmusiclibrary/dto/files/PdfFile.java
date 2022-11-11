package fr.wollfie.sheetmusiclibrary.dto.files;

import java.io.File;

public record PdfFile (
    File file
) implements ExecutableFile {
    @Override
    public void open() {
        
    }
}
