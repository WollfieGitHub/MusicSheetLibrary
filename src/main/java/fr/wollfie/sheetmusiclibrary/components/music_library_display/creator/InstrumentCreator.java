package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator;

import fr.wollfie.sheetmusiclibrary.IconChoice;
import fr.wollfie.sheetmusiclibrary.dto.Instrument;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class InstrumentCreator extends MetadataCreator<Instrument> {
    
    public InstrumentCreator(Consumer<Instrument> onMetadataCreated) {
        super(onMetadataCreated);

        VBox nameStep = new VBox();
        Label nameStepPrompt = new Label();

        IconChoice iconChoice = new IconChoice(Logger::info, 10);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new Insets(0))));
        
        getChildren().addAll(iconChoice);
    }
    
    
}
