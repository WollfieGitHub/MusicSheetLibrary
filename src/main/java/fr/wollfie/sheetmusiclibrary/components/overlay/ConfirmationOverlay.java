package fr.wollfie.sheetmusiclibrary.components.overlay;

import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.utils.FontSize;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class ConfirmationOverlay extends HBox implements Overlay{

    private final Consumer<Boolean> callback;
    
    public ConfirmationOverlay(boolean dangerous, Consumer<Boolean> callback) {
        this.callback = callback;

        ThemedButton confirmBtn = new ThemedButton(
                "Confirm", null,
                dangerous ? Theme.Category.Error : Theme.Category.Accent,
                FontSize.DEFAULT_H1
        );
        confirmBtn.setOnMouseClicked(e -> {
            OverlayDisplayHandler.hideOverlay();
            this.callback.accept(true);
        });
        
        ThemedButton cancelBtn = new ThemedButton(
                "Cancel", null,
                Theme.Category.Primary,
                FontSize.DEFAULT_H1
        );
        cancelBtn.setOnMouseClicked(e -> {
            OverlayDisplayHandler.hideOverlay();
            this.callback.accept(false);
        });
        
        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().setAll(cancelBtn, confirmBtn);
    }

    @Override
    public void mounted() { /* DO NOTIHNG */ }

    @Override
    public Node getNode() {
        return this;
    }
    
    public static void prompt(Consumer<Boolean> onChoiceDone, boolean dangerous) {
        OverlayDisplayHandler.showOverlay(new ConfirmationOverlay(dangerous, onChoiceDone));
    }
}
