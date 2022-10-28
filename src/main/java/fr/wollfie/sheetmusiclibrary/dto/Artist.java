package fr.wollfie.sheetmusiclibrary.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The author of a Sheet Music 
 * @param firstName First name of the author
 * @param lastName Last name of the author
 * @param yearOfBirth Year of birth of the author
 * @param yearOfDeath Year of death of the author if any 
 * @param musicGenres Music genre(s) the author is associated with
 */
public record Artist(
        String firstName,
        String lastName,
        int yearOfBirth,
        Optional<Integer> yearOfDeath,
        List<MetadataRef<MusicGenre>> musicGenres
) implements Metadata {

    public Artist(String firstName, String lastName, int yearOfBirth, Optional<Integer> yearOfDeath,
                  MusicGenre... musicGenres) {
        this(firstName, lastName, yearOfBirth, yearOfDeath, Arrays.stream(musicGenres).map(MetadataRef::new).toList());
    }

    @Override
    public List<String> getSearchableTokenFields() {
        return Arrays.asList(
                firstName,
                lastName,
                String.valueOf(yearOfBirth)
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (yearOfBirth != artist.yearOfBirth) return false;
        if (!firstName.equals(artist.firstName)) return false;
        return lastName.equals(artist.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + yearOfBirth;
        return result;
    }
}
