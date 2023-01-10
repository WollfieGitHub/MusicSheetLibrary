package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.wollfie.sheetmusiclibrary.dto.files.MusescoreFile;
import fr.wollfie.sheetmusiclibrary.dto.files.PdfFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A sheet of music
 */
public final class SheetMusic extends MetadataObject {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    private String name;
    private MetadataRef<Artist> artistRef;
    private List<Track> tracks;
    private MusicCategory category;
    private PdfFile pdfFile;
    private MusescoreFile musescoreFile;

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTOR                                    ||
// ||                                                                                      ||
// \\======================================================================================//

    public SheetMusic() {}

    /**
     * @param name          The name of the music
     * @param artistRef     The reference to the author of the sheet
     * @param tracks        The instruments(s) the sheet music contains direction for and the main key they are written in
     * @param pdfFile       The pdf file associated with the sheet music
     * @param musescoreFile The musescore file associated with the sheet music
     * @param category      The category of music this sheet fits in
     */
    public SheetMusic(
            String name,
            MetadataRef<Artist> artistRef,
            List<Track> tracks,
            MusicCategory category,
            PdfFile pdfFile,
            MusescoreFile musescoreFile
    ) {
        this.name = name;
        this.artistRef = artistRef;
        this.tracks = tracks;
        this.category = category;
        this.pdfFile = pdfFile;
        this.musescoreFile = musescoreFile;
    }
    
    public SheetMusic(String name, PdfFile file) {
        this.name = name;
        this.pdfFile = file;
        this.tracks = new ArrayList<>();
    }

    public SheetMusic(String name, Artist artistRef, List<Track> tracks, MusicCategory category, PdfFile pdfFile,
                      MusescoreFile musescoreFile) {
        this(name, new MetadataRef<>(artistRef), tracks, category, pdfFile, musescoreFile);
    }

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       GETTERS                                        ||
// ||                                                                                      ||
// \\======================================================================================//
    
    /**
     * @return the name of the sheet music followed by the name of the artistRef separated by a "-"
     */
    public String fullName() {
        return name + (artistRef != null ? " - " + artistRef.getValue().getName() : "");
    }
    
    @Override
    public List<String> getSearchableTokenFields() {
        return Collections.singletonList(fullName());
    }
    
    @JsonIgnore public boolean isArtistKnown() { return artistRef != null; }

    @JsonProperty("name") public String getName() { return name; }
    @JsonProperty("artistRef") public MetadataRef<Artist> getArtistRef() { return artistRef; }
    @JsonProperty("tracks") public List<Track> getTracks() {  return tracks; }
    @JsonProperty("category") public MusicCategory getCategory() { return category; }
    @JsonProperty("pdfFile") public PdfFile getPdfFile() { return pdfFile; }
    @JsonProperty("musescoreFile") public MusescoreFile getMusescoreFile() { return musescoreFile; }

    @JsonProperty("name") public void setName(String name) { this.name = name; }
    @JsonProperty("artistRef") public void setArtistRef(MetadataRef<Artist> artistRef) { this.artistRef = artistRef; }
    @JsonProperty("tracks") public void setTracks(List<Track> tracks) { this.tracks = tracks; }
    @JsonProperty("category") public void setCategory(MusicCategory category) { this.category = category; }
    @JsonProperty("pdfFile") public void setPdfFile(PdfFile pdfFile) { this.pdfFile = pdfFile; }
    @JsonProperty("musescoreFile") public void setMusescoreFile(MusescoreFile musescoreFile) { this.musescoreFile = musescoreFile; }

    @Override
    public String toString() {
        return "SheetMusic[" +
                "name=" + name + ", " +
                "artistRef=" + artistRef + ", " +
                "tracks=" + tracks + ", " +
                "category=" + category + ", " +
                "pdfFile=" + pdfFile + ", " +
                "musescoreFile=" + musescoreFile + ']';
    }


}