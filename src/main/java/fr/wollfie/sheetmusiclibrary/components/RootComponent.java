package fr.wollfie.sheetmusiclibrary.components;

import fr.wollfie.sheetmusiclibrary.components.item_pages.PageDisplayHandler;
import fr.wollfie.sheetmusiclibrary.components.music_library_display.LibraryDisplay;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RootComponent extends BorderPane {

    private static RootComponent instance;
    
    private final StackPane centerContent;
    private final LibraryDisplay libraryComponent;
    private final PageDisplayHandler pageComponent;
    
    public RootComponent(Stage stage) {
        instance = this;
        
        // Load default styleSheets
        getStylesheets().addAll(
                "noheader.css",
                "main.css",
                "scrollbar.css",
                "titled_pane.css",
                "checkbox.css"
        );
        
        // Close, Maximize, Minimize buttons
        TopToolbar topToolbar = new TopToolbar(stage);
        setTop(topToolbar);

        // Center display
        centerContent = new StackPane();
        centerContent.setAlignment(Pos.TOP_CENTER);
        VBox center = new VBox(new SubToolbar(), centerContent);
        setCenter(center);
        
        // Library display
        libraryComponent = LibraryDisplay.createNew();
        // Page display handler
        pageComponent = PageDisplayHandler.createNew();

        // Left panel : Add, Import
        setLeft(new LeftToolbar());

        // Init the component's style
        initStyle(topToolbar);
        displayLibrary();
    }
    
    /** Display the library of items in the center pane of the application */
    public static void displayLibrary() { 
        instance.centerContent.getChildren().setAll(instance.libraryComponent);
    }
    
    /** Display the page of an item in the center pane of the application */
    public static void displayPage(Metadata item) {
        instance.centerContent.getChildren().setAll(instance.pageComponent);
        instance.pageComponent.displayContentFor(item);
    }
    
    /** Init the style of the component and adapt it depending on
     *  the {@link fr.wollfie.sheetmusiclibrary.components.TopToolbar#stageMaximizedProperty()} */
    private void initStyle(TopToolbar topToolbar) {
        // Set light thin borders
        String borderStyle = "-fx-border-width: 1;" +
                "-fx-border-radius: 10 10 15 15;" +
                "-fx-border-color: " + "grey" + ";";

        // Background style is a gradient from black to the upper lighter primary color 
        String backgroundStyle = ("-fx-background-color: linear-gradient(to top," +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark2) + ", " +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Dark1) + " 40%, " +
                ThemeManager.hexColorFrom(Theme.Category.Background, Theme.Shade.Default) + " 50%, " +
                ThemeManager.hexColorFrom(Theme.Category.Primary, Theme.Shade.Dark1) + " 90%, " +
                ThemeManager.hexColorFrom(Theme.Category.Primary, Theme.Shade.Default) + " 110%);");

        setStyle(backgroundStyle + borderStyle + "-fx-background-radius: 15");

        // Remove unnecessary flourishes when in full screen
        topToolbar.stageMaximizedProperty().addListener((o, oldB, newB) -> {
            setStyle(backgroundStyle + (newB ? "-fx-background-radius: 0;" : borderStyle + "-fx-background-radius: 15;"));
        });
    }


}
