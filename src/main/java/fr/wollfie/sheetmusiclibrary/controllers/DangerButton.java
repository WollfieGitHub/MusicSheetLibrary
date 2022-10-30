package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class DangerButton extends ThemedButton {
    
    public DangerButton(String s, Node graphic) {
        super(s, graphic, Theme.Category.Error);
    }

    @Override
    protected void onUpdate() {
        Theme.Shade shade = switch (currentState) {
            case Default -> Theme.Shade.Default;
            case Hovered -> Theme.Shade.Light1;
            case Pressed -> Theme.Shade.Light2;
        };

        setBackground(new Background(new BackgroundFill(
                ThemeManager.colorFrom(currentState != State.Default ? Theme.Category.Error : Theme.Category.Primary, shade),
                new CornerRadii(cornerRadii), new Insets(0)
        )));
    }
}
