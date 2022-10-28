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
    protected static final int cornerRadii = 5;
    
    private State currentState = State.Default;
    private boolean hovering;
    
    public ThemedButton(String s, Node graphic, Theme.Category category) {
        super(s, graphic);
        
        this.category = category;
        init();
        onUpdate(currentState);
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
            onUpdate(currentState);
        });
        
        setOnMouseExited(mouseEvent -> {
            currentState = State.Default;
            hovering = false;
            onUpdate(currentState);
        });
        
        setOnMousePressed(mouseEvent -> {
            currentState = State.Pressed;
            onUpdate(currentState);
        });
        
        setOnMouseReleased(mouseEvent -> {
            currentState = hovering ? State.Hovered : State.Default;
            onUpdate(currentState);
        });
    }
    
    protected void onUpdate(State state) {
        Theme.Shade shade = switch (state) {
            case Default -> Theme.Shade.Default;
            case Hovered -> Theme.Shade.Light1;
            case Pressed -> Theme.Shade.Light2;
        };
        
        setBackground(new Background(new BackgroundFill(
                ThemeManager.colorFrom(category, shade),
                new CornerRadii(cornerRadii), new Insets(0)
        )));
    }
    
    protected void onHover() { }
    protected void onClick() { }
    
}
