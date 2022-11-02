package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * A button using the theme set in the ThemeManager.
 */
public class ThemedButton extends Button {

    protected final Theme.Category category;
    protected int cornerRadii = 5;
    protected Color backgroundColor;
    
    protected State currentState = State.Default;
    protected boolean hovering;
    private Node graphic;
    private int fontSize;

    public ThemedButton(String s, Node graphic, Theme.Category category) { 
        this(s, graphic, category, 11);
    }
    
    /**
     * A button using the theme set in the ThemeManager
     * @param s : The string to display in the button, can be null
     * @param graphic : The Icon to display in the button, can be null
     * @param category : The Theme category the button should have
     */
    public ThemedButton(String s, Node graphic, Theme.Category category, int fontSize) {
        super(s, graphic);
        this.graphic = graphic;
        this.category = category;
        this.fontSize = fontSize;
        init();
        onUpdate();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadii = radius;
    }

    protected enum State {
        Default,
        Hovered,
        Pressed
    }

    protected void init() {
               
        setOnMouseEntered(mouseEvent -> {
            currentState = State.Hovered;
            hovering = true;
            onUpdate();
        });
        
        setOnMouseExited(mouseEvent -> {
            currentState = State.Default;
            hovering = false;
            onUpdate();
        });
        
        setOnMousePressed(mouseEvent -> {
            currentState = State.Pressed;
            onUpdate();
        });
        
        setOnMouseReleased(mouseEvent -> {
            currentState = hovering ? State.Hovered : State.Default;
            onUpdate();
        });
    }
    
    protected void onUpdate() {
        Theme.Shade shade = switch (currentState) {
            case Default -> Theme.Shade.Default;
            case Hovered -> Theme.Shade.Light1;
            case Pressed -> Theme.Shade.Light2;
        };
        
        backgroundColor = ThemeManager.colorFrom(category, shade);
        applyStyle();
    }

    protected void applyStyle() {
        Color contentColor = ThemeManager.getTextColorFrom(backgroundColor);
        contentColor = hovering ?  contentColor : contentColor.deriveColor(
                0, 1, 0.7, 1
        );
        
        setStyle("-fx-background-color: " + Utils.toRGBCode(backgroundColor) + ";" +
                "-fx-background-radius: " + cornerRadii + "%;" +
                "-fx-text-fill: " + Utils.toRGBCode(contentColor) + ";" +
                "-fx-font-size: " + fontSize + ";" +
                (hovering ? "-fx-cursor: hand" : ""));
        if (graphic != null) {
            graphic.setStyle(graphic.getStyle() + "-fx-icon-color: " + Utils.toRGBCode(contentColor) + ";" +
                    "-fx-icon-size: " + fontSize + ";");
        }
    }

    protected void onHover() { }
    protected void onClick() { }
    
}
