package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class LazyImageUrl implements JsonSerializable {

    private static final int DEFAULT_SIZE = 300;
    
    public String imageUrl;
    public boolean fetched;
    public boolean found;
    @JsonIgnore private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    public ReadOnlyObjectProperty<Image> imageProperty() {return image; }

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
        this.image.set(image);
    }

    public void setFrom(String url) {
        this.imageUrl = url;
        this.fetched = true;
        this.found = url != null;
        this.loadImage();
    }
    
    public static LazyImageUrl empty() {
        return new LazyImageUrl("", false, false, null);
    }
    
    @JsonIgnore public Image getImage() {
        if (this.available()) { return image.get(); }
        return null;
    }
    
    private void loadImage() {
        this.image.set(
            imageUrl == null 
                ? null 
                : new Image(imageUrl, DEFAULT_SIZE, DEFAULT_SIZE, 
                    true, true, false)
        );
    }
    
    @JsonIgnore public boolean wasNotFound() { return fetched && !found; }

    @JsonIgnore public boolean available() {
        return fetched && found && !image.getValue().isError();
    }
}
