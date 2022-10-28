package fr.wollfie.sheetmusiclibrary.dto;

import fr.wollfie.sheetmusiclibrary.io.MusescoreFile;
import fr.wollfie.sheetmusiclibrary.io.PdfFile;

import java.util.Optional;

public record SheetMusic(
        String name,
        Artist artist,
        Instrument instrument,
        PdfFile pdfFile,
        Optional<MusescoreFile> musescoreFile
) {

    /** @return the name of the sheet music followed by the name of the artist separated by a "-" */
    public String fullName() {
        return name + " - " + artist.lastName();
    }
    
}