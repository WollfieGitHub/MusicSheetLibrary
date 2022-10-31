package fr.wollfie.sheetmusiclibrary.io.network;

import java.io.IOException;

public class ArtistNotFoundException extends IOException {

    public ArtistNotFoundException() {
    }

    public ArtistNotFoundException(String message) {
        super(message);
    }
}
