package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import javafx.scene.layout.HBox;

public class MusicSheetItemDisplay extends HBox {
    
    private MusicSheetItemDisplay(SheetMusic sheetMusic) {
        
    }
    
    public static MusicSheetItemDisplay from(SheetMusic sheetMusic) {
        return new MusicSheetItemDisplay(sheetMusic);
    }
}
