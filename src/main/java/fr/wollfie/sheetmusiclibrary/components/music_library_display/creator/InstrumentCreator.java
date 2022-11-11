package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.ColorPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.FontIconPrompt;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts.StringPrompt;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.utils.Language;
import fr.wollfie.sheetmusiclibrary.utils.LingualString;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;
import java.util.function.Function;

public class InstrumentCreator extends MetadataCreator<Instrument> {
    
    private final StringProperty nameProperty = new SimpleStringProperty(null);
    private final StringProperty frenchNameProperty = new SimpleStringProperty(null);
    private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<FontIcon> iconProperty = new SimpleObjectProperty<>(null);
    private final StringPrompt englishPrompt;

    public InstrumentCreator(Consumer<Instrument> onMetadataCreated) {
        super(onMetadataCreated);

        ColorPrompt colorPrompt = new ColorPrompt("Icon Color", onResultFinish(colorProperty));
        FontIconPrompt iconChoice = new FontIconPrompt("Instrument Icon", onResult(iconProperty, colorPrompt));
        StringPrompt frenchPrompt = new StringPrompt("French Instrument Name", onResult(frenchNameProperty, iconChoice));
        englishPrompt = new StringPrompt("English Instrument Name", onResult(nameProperty, frenchPrompt));
        
        getChildren().addAll(englishPrompt);
    }

    @Override
    protected void onPreFinish() {
        this.iconProperty.get().setIconColor(this.colorProperty.get());
    }

    @Override
    protected <T> Function<T, Instrument> finalValueToResult() {
        return finalValue -> new Instrument(
                new LingualString(
                        Tuple.of(Language.ENGLISH, this.nameProperty.get()),
                        Tuple.of(Language.FRENCH, this.frenchNameProperty.get())
                ),
                (Color) finalValue,
                this.iconProperty.get()
        );
    }

    @Override
    public void mounted() {
        englishPrompt.getFocus();
    }


}
