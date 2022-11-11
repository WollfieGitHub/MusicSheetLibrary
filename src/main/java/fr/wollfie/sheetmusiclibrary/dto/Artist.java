package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    
    private final String firstNameOrNickname;
    private final Optional<String> lastName;
    private final int yearOfBirth;
    private final Optional<Integer> yearOfDeath;
    private final List<MetadataRef<MusicGenre>> musicGenres;
    private final LazyImageUrl imageUrl;

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
                  @JsonProperty("lastName") Optional<String> lastName,
                  @JsonProperty("yearOfBirth") int yearOfBirth,
                  @JsonProperty("yearOfDeath") Optional<Integer> yearOfDeath,
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
    
    public Artist(String firstName, Optional<String> lastName, int yearOfBirth, Optional<Integer> yearOfDeath,
                  LazyImageUrl imageUrl, MusicGenre... musicGenres) {
        this(firstName, lastName,
                yearOfBirth, yearOfDeath,
                Arrays.stream(musicGenres).map(MetadataRef::new).toList(),
                imageUrl);
    }

    public Artist withImage(LazyImageUrl image) {
        return new Artist(
                this.firstNameOrNickname, this.lastName,
                this.yearOfBirth, this.yearOfDeath,
                this.musicGenres,
                image
        );
    }

    public Artist(String firstName, String lastName, int yearOfBirth, Optional<Integer> yearOfDeath,
                  MusicGenre... musicGenres) {
        this(firstName, Optional.ofNullable(lastName), yearOfBirth, yearOfDeath, LazyImageUrl.empty(), musicGenres);
    }

    public Artist(String firstName, Optional<String> lastName, int yearOfBirth, Optional<Integer> yearOfDeath,
                  MusicGenre... musicGenres) {
        this(firstName, lastName, yearOfBirth, yearOfDeath, LazyImageUrl.empty(), musicGenres);
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
        lastName.ifPresent(result::add);

        return result;
    }

    @JsonIgnore public String fullName() {
        return firstNameOrNickname + lastName.map(name -> " " + name).orElse("");
    }
    @JsonIgnore public String formattedDates() {
        return String.format("(%d - %s)", yearOfBirth, yearOfDeath.map(String::valueOf).orElse("   "));
    }
    
    public String getFirstNameOrNickname() {
        return firstNameOrNickname;
    }
    public Optional<String> getLastName() {
        return lastName;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public Optional<Integer> getYearOfDeath() {
        return yearOfDeath;
    }
    public List<MetadataRef<MusicGenre>> getMusicGenres() {
        return musicGenres;
    }
    public LazyImageUrl getImageUrl() {
        return imageUrl;
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
