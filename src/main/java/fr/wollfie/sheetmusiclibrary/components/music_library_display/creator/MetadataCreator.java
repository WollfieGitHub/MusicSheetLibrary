package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ValuePrompt;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MetadataCreator<M extends Metadata> extends StackPane {

    protected final Consumer<M> onMetadataCreated;
    
    public MetadataCreator(Consumer<M> onMetadataCreated) {
        this.onMetadataCreated = onMetadataCreated;
        
        String backgroundColor = Utils.toRGBCode(ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Dark2)
                .deriveColor(0, 1, 1, 0.8));
        
        setAlignment(Pos.CENTER);
        setPadding(new Insets(100, 300, 100, 300));
        setStyle("-fx-background-color: " + backgroundColor + ";" +
                "-fx-background-radius: 25;" +
                "-fx-background-insets: 2");
        setViewOrder(0);
        requestFocus();
        setOnKeyPressed(Utils.onKeyTyped(KeyCode.ESCAPE, CreatorDisplayHandler::hideCreator));
    }
    
    protected <T> Consumer<T> onResult(Property<T> property, ValuePrompt<?> nextNode) {
        return val -> {
            property.setValue(val);
            swapTo(nextNode);
        };
    }
    
    protected void swapTo(ValuePrompt<?> node) {
        getChildren().setAll(node);
        node.getFocus();
    }
    
    protected <T> Consumer<T> onResultFinish() {
        return finalValue -> {
            this.onMetadataCreated.accept(finalValueToResult().apply(finalValue));
            CreatorDisplayHandler.hideCreator();
        };
    }
    
    protected abstract <T> Function<T, M> finalValueToResult();

    public abstract void mounted();
}
