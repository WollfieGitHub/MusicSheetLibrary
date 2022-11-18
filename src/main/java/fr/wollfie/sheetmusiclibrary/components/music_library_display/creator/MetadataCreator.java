package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ValuePrompt;
import fr.wollfie.sheetmusiclibrary.components.overlay.Overlay;
import fr.wollfie.sheetmusiclibrary.components.overlay.OverlayDisplayHandler;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import fr.wollfie.sheetmusiclibrary.dto.MetadataType;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Callback;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;


import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MetadataCreator<M extends MetadataObject> extends StackPane implements Overlay {

    protected final Consumer<M> onMetadataCreated;
    
    public MetadataCreator(Consumer<M> onMetadataCreated) {
        this.onMetadataCreated = onMetadataCreated;
        
        setAlignment(Pos.CENTER);
        requestFocus();
        setOnKeyPressed(Utils.onKeyTyped(KeyCode.ESCAPE, OverlayDisplayHandler::hideOverlay));
    }

    public static void promptCreationFor(MetadataType currentSelectedType) {
        MetadataCreator<?> creator = switch (currentSelectedType) {
            case Artist -> new ArtistCreator(SheetMusicLibrary::tryInsert);
            case Instrument -> new InstrumentCreator(SheetMusicLibrary::tryInsert);
            case MusicGenre -> new MusicGenreCreator(SheetMusicLibrary::tryInsert);
            case MusicCategory -> new MusicCategoryCreator(SheetMusicLibrary::tryInsert);
            case SheetMusic -> new SheetMusicCreator(SheetMusicLibrary::tryInsert);
        };
        OverlayDisplayHandler.showOverlay(creator);
    }

    protected <T> Callback<T> onResult(Property<T> property, ValuePrompt<?> nextNode) {
        return val -> {
            property.setValue(val);
            swapTo(nextNode);
        };
    }
    
    protected void swapTo(ValuePrompt<?> node) {
        getChildren().setAll(node);
        node.getFocus();
    }
    
    protected <T> Callback<T> onResultFinish(Property<T> finalProperty) {
        return finalValue -> {
            finalProperty.setValue(finalValue);
            this.onPreFinish();
            this.onMetadataCreated.accept(finalValueToResult().apply(finalValue));
            OverlayDisplayHandler.hideOverlay();
        };
    }

    protected abstract void onPreFinish();

    protected abstract <T> Function<T, M> finalValueToResult();

    @Override
    public Node getNode() { return this; }
}
