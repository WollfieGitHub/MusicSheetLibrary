package fr.wollfie.sheetmusiclibrary.controllers;

import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import fr.wollfie.sheetmusiclibrary.theme.ThemeManager;
import fr.wollfie.sheetmusiclibrary.utils.NodeColorFinder;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import static java.lang.Math.*;

public class Card extends VBox {
    
    private static final int CARD_WIDTH = 150;

    public Card(Node content, Node label) {
        
        StackPane contentPane = new StackPane();
        getChildren().setAll(contentPane, label);
        
        setMaxWidth(CARD_WIDTH);
        setPrefWidth(CARD_WIDTH);
        setMinWidth(CARD_WIDTH);
        
        setSpacing(10);
        setPadding(new Insets(10));

        Color backgroundColor = ThemeManager.colorFrom(Theme.Category.Background, Theme.Shade.Light1);

        String styleString = Utils.toRGBCode(backgroundColor);
        if (content != null) {
            contentPane.getChildren().setAll(content);
            Color color = NodeColorFinder.getAverageColorOf(content);
            styleString = String.format(
                    // language=CSS-FX
                    "-fx-background-color: linear-gradient(to top, %s, %s 10%%, %s 20%%,  %s 40%%);",
                    Utils.toRGBCode(color),
                    Utils.toRGBCode(Utils.interpolate(color, backgroundColor)),
                    Utils.toRGBCode(Utils.interpolate(Utils.interpolate(color, backgroundColor), backgroundColor)),
                    Utils.toRGBCode(backgroundColor));
            styleString  += String.format(
                    // language=CSS-FX
                    "-fx-border-color: %s; -fx-border-width: 0 1 1 1;",
                    Utils.toRGBCode(color));
        }
        
        setStyle(
                styleString + 
                "-fx-border-radius: 25;" +
                "-fx-background-radius: 25;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2) ,10 , 0.5 , 0 , 0);"
        );
        
        contentPane.setStyle(
                "-fx-background-color: transparent;"
        );
        
        // sqrt(2)/4  | 1/2
        // 100

        setAlignment(Pos.CENTER);
        
    }

}
