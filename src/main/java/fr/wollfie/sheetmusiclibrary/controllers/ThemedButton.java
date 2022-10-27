package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class ThemedButton extends Button {

    private final Theme.Category category;
    private final int cornerRadii = 5;
    
    public ThemedButton(String s, Node graphic, Theme.Category category) {
        super(s, graphic);
        
        this.category = category;
        init();
    }

    private void init() {
        setBackground(new Background(new BackgroundFill(
                ThemeManager.colorFrom(category, Theme.Shade.Default),
                new CornerRadii(cornerRadii),
                new Insets(0)
        )));
        
        setOnMouseEntered(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(
                    ThemeManager.colorFrom(category, Theme.Shade.Light1),
                    new CornerRadii(cornerRadii),
                    new Insets(0)
            )));
        });
        
        setOnMouseExited(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(
                    ThemeManager.colorFrom(category, Theme.Shade.Default),
                    new CornerRadii(cornerRadii),
                    new Insets(0)
            )));
        });
        
        setOnMousePressed(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(
                    ThemeManager.colorFrom(category, Theme.Shade.Light2),
                    new CornerRadii(cornerRadii),
                    new Insets(0)
            )));
        });
        
        setOnMouseReleased(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(
                    ThemeManager.colorFrom(category, Theme.Shade.Default),
                    new CornerRadii(cornerRadii),
                    new Insets(0)
            )));
        });
    }
    
    protected void onHover() { }
    protected void onClick() { }
    
}
