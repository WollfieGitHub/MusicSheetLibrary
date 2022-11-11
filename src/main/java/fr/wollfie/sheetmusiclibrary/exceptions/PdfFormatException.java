package fr.wollfie.sheetmusiclibrary.exceptions;

import java.io.IOException;

public class PdfFormatException extends IOException {

    public PdfFormatException() {
        super("The specified PDF file is not recognized as a sheet music");
    }
    
    
}
