package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ColorPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.IconPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.TextPrompt;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;
import java.util.function.Function;

public class InstrumentCreator extends MetadataCreator<Instrument> {
    
    private final StringProperty nameProperty = new SimpleStringProperty(null);
    private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<FontIcon> iconProperty = new SimpleObjectProperty<>(null);
    
    public InstrumentCreator(Consumer<Instrument> onMetadataCreated) {
        super(onMetadataCreated);

        ColorPrompt colorPrompt = new ColorPrompt("Icon Color", onResultFinish());
        IconPrompt iconChoice = new IconPrompt("Instrument Icon", onResult(iconProperty, colorPrompt));
        TextPrompt textPrompt = new TextPrompt("Instrument Name", onResult(nameProperty, iconChoice));
        
        getChildren().addAll(textPrompt);
    }

    @Override
    protected <T> Function<T, Instrument> finalValueToResult() {
        return finalValue -> new Instrument(
                this.nameProperty.get(),
                (Color) finalValue,
                this.iconProperty.get()
        );
    }


}
