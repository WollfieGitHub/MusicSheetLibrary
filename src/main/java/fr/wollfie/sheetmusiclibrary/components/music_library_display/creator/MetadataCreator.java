package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MetadataCreator<M extends Metadata> extends VBox {

    protected final Consumer<M> onMetadataCreated;
    
    public MetadataCreator(Consumer<M> onMetadataCreated) {
        this.onMetadataCreated = onMetadataCreated;
        
        String backgroundColor = Utils.toRGBCode(ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Dark2)
                .deriveColor(0, 1, 1, 0.42));
        
        setStyle("-fx-background-color: " + backgroundColor + ";");
        setViewOrder(0);
    }
    
    protected <T> Consumer<T> onResult(Property<T> property, Node nextNode) {
        return val -> {
            property.setValue(val);
            swapTo(nextNode);
        };
    }
    
    protected void swapTo(Node node) {
        getChildren().setAll(node);
    }
    
    protected <T> Consumer<T> onResultFinish() {
        return finalValue -> this.onMetadataCreated.accept(finalValueToResult().apply(finalValue));
    }
    
    protected abstract <T> Function<T, M> finalValueToResult();
    
}
