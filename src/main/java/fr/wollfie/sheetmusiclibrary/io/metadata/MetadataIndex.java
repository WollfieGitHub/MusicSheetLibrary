package fr.wollfie.sheetmusiclibrary.io.metadata;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.io.serialization.SerializationEngine;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetadataIndex<M extends Metadata> {
    
    private final File file;
    public final List<M> metadata;

    private MetadataIndex(List<M> metadata, File file) {
        this.metadata = new ArrayList<>(metadata);
        this.file = file;
        reloadIndices();
    }

    public static <M extends Metadata> MetadataIndex<M> createFrom(List<M> metadata, File file) {
        Preconditions.checkNotNull(metadata);
        return new MetadataIndex<>(metadata, file);
    }
    
    public void add(M metadata) throws IOException {
        this.metadata.add(metadata);
        save();
        reloadIndices();
    }

    private void reloadIndices() {
        // TODO
    }
    
    private void save() throws IOException {
        SerializationEngine.saveAllTo(file, this.metadata);
    }
}
