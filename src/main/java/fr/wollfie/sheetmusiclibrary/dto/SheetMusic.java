package fr.wollfie.sheetmusiclibrary.dto;

import fr.wollfie.sheetmusiclibrary.library.MusescoreFile;
import fr.wollfie.sheetmusiclibrary.library.PdfFile;

import java.util.List;

/**
 * A sheet of music
 * @param name The name of the music 
 * @param artistRef The reference to the author of the sheet
 * @param instrument The instrument(s) the sheet music contains direction for
 * @param pdfFile The pdf file associated with the sheet music
 * @param musescoreFile The musescore file associated with the sheet music
 */
public record SheetMusic(
        String name,
        MetadataRef<Artist> artistRef,
        List<MetadataRef<Instrument>> instrument,
        PdfFile pdfFile,
        MusescoreFile musescoreFile
) implements Metadata {

    public SheetMusic(String name, Artist artistRef, List<Instrument> instrument, PdfFile pdfFile,
                      MusescoreFile musescoreFile) {
        this(name, new MetadataRef<>(artistRef), instrument.stream().map(MetadataRef::new).toList(),
                pdfFile, musescoreFile);
    }

    /** @return the name of the sheet music followed by the name of the artistRef separated by a "-" */
    public String fullName() {
        return name + " - " + artistRef.getValue().lastName();
    }


    @Override
    public List<String> getSearchableTokenFields() {
        return null;
        // TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SheetMusic that = (SheetMusic) o;

        if (!name.equals(that.name)) return false;
        if (!artistRef.equals(that.artistRef)) return false;
        return instrument.equals(that.instrument);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + artistRef.hashCode();
        result = 31 * result + instrument.hashCode();
        return result;
    }
}