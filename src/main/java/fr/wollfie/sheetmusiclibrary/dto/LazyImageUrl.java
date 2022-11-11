package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;
import javafx.scene.image.Image;
import org.w3c.dom.ls.LSException;

public class LazyImageUrl implements JsonSerializable {

    private static final int DEFAULT_SIZE = 300;
    
    public String imageUrl;
    public boolean fetched;
    public boolean found;
    @JsonIgnore private Image image;

    @JsonProperty("imageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        if (this.imageUrl != null) {
            this.loadImage();
        }
    }
    
    public LazyImageUrl() {}
    
    public LazyImageUrl(String imageUrl, boolean fetched, boolean found, Image image) {
        this.imageUrl = imageUrl;
        this.fetched = fetched;
        this.found = found;
        this.image = image;
    }

    public LazyImageUrl(String imageUrl, boolean fetched, boolean found) {
        this.imageUrl = imageUrl;
        this.fetched = fetched;
        this.found = found;
        this.loadImage();
    }

    public static LazyImageUrl fromResult(String url) {
        if (url == null) { return new LazyImageUrl(null, true, false, null); }
        else { return new LazyImageUrl(url, true, true); }
    }
    
    public static LazyImageUrl empty() {
        return new LazyImageUrl("", false, false, null);
    }
    
    @JsonIgnore public Image getImage() {
        if (this.available()) { return image; }
        return null;
    }
    
    private void loadImage() {
        this.image = imageUrl == null ? null : new Image(imageUrl, DEFAULT_SIZE, DEFAULT_SIZE, true, true, false);
    }

    @JsonIgnore public boolean available() {
        return fetched && found;
    }
}
