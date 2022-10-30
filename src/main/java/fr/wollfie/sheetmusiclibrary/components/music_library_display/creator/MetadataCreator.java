package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.scene.layout.VBox;


import java.util.function.Consumer;

public abstract class MetadataCreator<M extends Metadata> extends VBox {

    protected final Consumer<M> onMetadataCreated;
    
    public MetadataCreator(Consumer<M> onMetadataCreated) {
        this.onMetadataCreated = onMetadataCreated;
        
        String backgroundColor = Utils.toRGBCode(ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Dark2)
                .deriveColor(0, 1, 1, 0.2));
        
        setStyle("-fx-background-color: " + backgroundColor + ";");
    }
    
}
