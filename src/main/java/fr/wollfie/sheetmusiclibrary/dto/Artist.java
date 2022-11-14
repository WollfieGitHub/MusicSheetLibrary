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

    
    private String firstNameOrNickname;
    private String lastName;
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
     * @param firstNameOrNickname First name of the author
     * @param lastName            Last name of the author
     * @param yearOfBirth         Year of birth of the author
     * @param yearOfDeath         Year of death of the author if any
     * @param musicGenres         Music genre(s) the author is associated with
     * @param imageUrl            The url of the profile picture of the artist
     */
    @JsonCreator
    public Artist(@JsonProperty("firstNameOrNickname") String firstNameOrNickname,
                  @JsonProperty("lastName") String lastName,
                  @JsonProperty("yearOfBirth") int yearOfBirth,
                  @JsonProperty("yearOfDeath") Integer yearOfDeath,
                  @JsonProperty("musicGenres") List<MetadataRef<MusicGenre>> musicGenres,
                  @JsonProperty("imageUrl") LazyImageUrl imageUrl) {
        this.firstNameOrNickname = firstNameOrNickname;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
        this.musicGenres = musicGenres;
        this.imageUrl = imageUrl;
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       OTHER CONSTRUCTORS                             ||
// ||                                                                                      ||
// \\======================================================================================//
    
    public Artist(String firstName, String lastName, int yearOfBirth, Integer yearOfDeath,
                  LazyImageUrl imageUrl, MusicGenre... musicGenres) {
        this(firstName, lastName, 
                yearOfBirth, yearOfDeath,
                Arrays.stream(musicGenres).map(MetadataRef::new).toList(),
                imageUrl);
    }

    public Artist(String firstName, String lastName, int yearOfBirth, Integer yearOfDeath, MusicGenre... musicGenres) {
        this(firstName, lastName,
                yearOfBirth, yearOfDeath,
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
                firstNameOrNickname,
                String.valueOf(yearOfBirth),
                fullName()
        ));
        if (lastName != null) { result.add(lastName); }

        return result;
    }

    @JsonIgnore public String fullName() {
        return firstNameOrNickname + (lastName != null ? " " + lastName : "");
    }
    
    public String getFirstNameOrNickname() { return firstNameOrNickname; }
    public void setFirstNameOrNickname(String newNickname) {
        this.firstNameOrNickname = newNickname;
        this.reloadImage();
    }
    
    public String getLastName() { return lastName; }
    public void setLastName(String newLastName) {
        if ("".equals(newLastName)) { this.lastName = null; }
        else { this.lastName = newLastName; }
    }
    
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public Integer getYearOfDeath() {
        return yearOfDeath;
    }
    public List<MetadataRef<MusicGenre>> getMusicGenres() {
        return musicGenres;
    }
    
    public LazyImageUrl getImageUrl() {
        return imageUrl;
    }

    /** Re-fetch an image for this artist */
    private void reloadImage() {
        this.imageUrl = LazyImageUrl.fromResult(ArtistImageRetriever.fetchFor(this));
    }
    
    @Override
    public String toString() {
        return "Artist[" +
                "firstNameOrNickname=" + firstNameOrNickname + ", " +
                "lastName=" + lastName + ", " +
                "yearOfBirth=" + yearOfBirth + ", " +
                "yearOfDeath=" + yearOfDeath + ", " +
                "musicGenres=" + musicGenres + ", " +
                "imageUrl=" + imageUrl + ']';
    }
    
    

}
