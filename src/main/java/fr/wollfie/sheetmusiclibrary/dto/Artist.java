package fr.wollfie.sheetmusiclibrary.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The author of a Sheet Music 
 * @param firstNameOrNickname First name of the author
 * @param lastName Last name of the author
 * @param yearOfBirth Year of birth of the author
 * @param yearOfDeath Year of death of the author if any 
 * @param musicGenres Music genre(s) the author is associated with
 */
public record Artist(
        String firstNameOrNickname,
        Optional<String> lastName,
        int yearOfBirth,
        Optional<Integer> yearOfDeath,
        List<MetadataRef<MusicGenre>> musicGenres
) implements Metadata {

    public Artist(String firstName, String lastName, int yearOfBirth, Optional<Integer> yearOfDeath,
                  MusicGenre... musicGenres) {
        this(firstName, Optional.ofNullable(lastName), yearOfBirth, yearOfDeath, Arrays.stream(musicGenres).map(MetadataRef::new).toList());
    }


    @Override
    public List<String> getSearchableTokenFields() {
        List<String> result = new ArrayList<>(Arrays.asList(firstNameOrNickname, String.valueOf(yearOfBirth), fullName()));
        lastName.ifPresent(result::add);
        
        return result;
    }
    
    public String fullName() {
        return firstNameOrNickname + lastName.map(name -> " " + name).orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (yearOfBirth != artist.yearOfBirth) return false;
        if (!firstNameOrNickname.equals(artist.firstNameOrNickname)) return false;
        return lastName.equals(artist.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstNameOrNickname.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + yearOfBirth;
        return result;
    }
    
    public String formattedDates() {
        return String.format("(%d - %s)", yearOfBirth, yearOfDeath.map(String::valueOf).orElse("   "));
    }
}
