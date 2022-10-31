package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;
import javafx.scene.image.Image;
import org.w3c.dom.ls.LSException;

public class LazyImageUrl implements JsonSerializable {

    public String imageUrl;
    public boolean fetched;
    public boolean found;
    @JsonIgnore private Image image;
    
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
        this.image = imageUrl == null ? null : new Image(imageUrl, true);
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

    @JsonIgnore public boolean available() {
        return fetched && found;
    }
}
