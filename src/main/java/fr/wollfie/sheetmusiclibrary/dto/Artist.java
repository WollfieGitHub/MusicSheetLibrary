package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.wollfie.sheetmusiclibrary.io.network.ArtistImageRetriever;

import java.util.*;

/**
 * The author of a Sheet Music
 */
public final class Artist extends MetadataObject {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//

    
    private String name;
    private int yearOfBirth;
    private Integer yearOfDeath;
    private final List<MetadataRef<MusicGenre>> musicGenres;
    private LazyImageUrl imageUrl;

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTORS                                   ||
// ||                                                                                      ||
// \\======================================================================================//
    
    /**
     * @param name name of the author
     * @param yearOfBirth         Year of birth of the author
     * @param yearOfDeath         Year of death of the author if any
     * @param musicGenres         Music genre(s) the author is associated with
     * @param imageUrl            The url of the profile picture of the artist
     */
    @JsonCreator
    public Artist(@JsonProperty("firstNameOrNickname") String name,
                  @JsonProperty("yearOfBirth") int yearOfBirth,
                  @JsonProperty("yearOfDeath") Integer yearOfDeath,
                  @JsonProperty("musicGenres") List<MetadataRef<MusicGenre>> musicGenres,
                  @JsonProperty("imageUrl") LazyImageUrl imageUrl) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
        this.musicGenres = musicGenres;
        this.imageUrl = imageUrl;
        
        // If the image was not found, maybe we had no connection while first searching for it,
        // retry to request an image for the artist
        if (this.imageUrl.wasNotFound()) { this.reloadImage(); }
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       OTHER CONSTRUCTORS                             ||
// ||                                                                                      ||
// \\======================================================================================//
    
    public Artist(String firstName, int yearOfBirth, Integer yearOfDeath,
                  LazyImageUrl imageUrl, MusicGenre... musicGenres) {
        this(firstName, yearOfBirth, yearOfDeath,
                Arrays.stream(musicGenres).map(MetadataRef::new).toList(),
                imageUrl);
    }

    public Artist(String firstName, int yearOfBirth, Integer yearOfDeath, MusicGenre... musicGenres) {
        this(firstName, yearOfBirth, yearOfDeath,
                Arrays.stream(musicGenres).map(MetadataRef::new).toList(),
                LazyImageUrl.empty());
        this.reloadImage();
    }

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       GETTERS                                        ||
// ||                                                                                      ||
// \\======================================================================================//

    @Override
    public List<String> getSearchableTokenFields() {
        List<String> result = new ArrayList<>(Arrays.asList(
                name,
                String.valueOf(yearOfBirth)
        ));

        return result;
    }
    
    public String getName() { return name; }
    public void setName(String newNickname) {
        this.name = newNickname;
        this.reloadImage();
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) { this.yearOfBirth = yearOfBirth; }
    
    public Integer getYearOfDeath() {
        return yearOfDeath;
    }
    public void setYearOfDeath(Integer yearOfDeath) { this.yearOfDeath = yearOfDeath; }

    public List<MetadataRef<MusicGenre>> getMusicGenres() {
        return musicGenres;
    }

    public LazyImageUrl getImageUrl() {
        return imageUrl;
    }

    /** Re-fetch an image for this artist */
    private void reloadImage() {
        this.imageUrl.setFrom(ArtistImageRetriever.fetchFor(this));
    }

    @Override
    public String toString() {
        return "Artist[" +
                "firstNameOrNickname=" + name + ", " +
                "yearOfBirth=" + yearOfBirth + ", " +
                "yearOfDeath=" + yearOfDeath + ", " +
                "musicGenres=" + musicGenres + ", " +
                "imageUrl=" + imageUrl + ']';
    }
}
