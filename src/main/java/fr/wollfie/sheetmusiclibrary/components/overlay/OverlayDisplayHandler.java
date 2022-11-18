package fr.wollfie.sheetmusiclibrary.components.overlay;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class OverlayDisplayHandler {
    
    private static Pane root;
    
    private static Overlay activeOverlay = null;
    private static final StackPane container = new StackPane();
    
    private static final String BACKGROUND_COLOR = Utils.toRGBCode(
            ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Dark2)
            .deriveColor(0, 1, 1, 0.8)
    );
    
    
    private OverlayDisplayHandler() { }

    public static void init(Pane root) {
        OverlayDisplayHandler.root = root;
        
        container.setPadding(new Insets(100));
        container.setViewOrder(0);
    }
    
    public static void showOverlay(Overlay overlay) {
        if (activeOverlay != null) { throw new UnsupportedOperationException("Cannot display creator when" +
                " one is already displayed"); }
        activeOverlay = overlay;
        
        display(overlay);
    }

    private static void display(Overlay overlay) {
        root.getChildren().add(container);
        
        container.getChildren().setAll(overlay.getNode());
        container.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";" +
                "-fx-background-radius: 25;" +
                "-fx-background-insets: 2");
        overlay.mounted();
    }

    public static void hideOverlay() {
        root.getChildren().remove(container);
        
        container.getChildren().clear();
        container.setStyle("-fx-background-color: transparent;");
        
        activeOverlay =  null;
    }

}
