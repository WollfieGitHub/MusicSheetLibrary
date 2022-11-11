package fr.wollfie.sheetmusiclibrary.utils;

import fr.wollfie.sheetmusiclibrary.exceptions.Errors;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public final class NodeColorFinder {
    private NodeColorFinder() {}
    
    public static Color getAverageColorOf(Node node) {
        return switch (node) {
            case FontIcon icon -> getAverageColorOf(icon.getIconColor());
            case Circle circle -> getAverageColorOf(circle.getFill());
            
            default -> Errors.ERROR_COLOR;
        };
    }
    
    public static Color getAverageColorOf(Paint paint) {
        Logger.info("Getting average color of " + paint);
        return switch (paint) {
            case Color color -> color;
            case ImagePattern pattern -> getAverageColorOfPattern(pattern);
            
            case default -> Errors.ERROR_COLOR;
        };
    }

    private static Color getAverageColorOfPattern(ImagePattern pattern) {
        Image image = pattern.getImage();
        PixelReader pr = image.getPixelReader();
        Logger.info("Getting color of pattern " + pattern);
        if (pr == null) { return Errors.ERROR_COLOR; }
        Logger.info("Did pass this");
        
        int[] buffer = new int[(int) (image.getWidth() * image.getHeight())];
        pr.getPixels(
                0, 0, (int)(image.getWidth()), (int)(image.getHeight()),
                PixelFormat.getIntArgbInstance(),
                buffer, 1, 0
        );
        
        double meanR = Arrays.stream(buffer).parallel().map(v -> (v >> 16) & 0xFF).reduce(Integer::sum).orElse(0)  / (double)buffer.length;
        double meanG = Arrays.stream(buffer).parallel().map(v -> (v >> 8) & 0xFF).reduce(Integer::sum).orElse(0)  / (double)buffer.length;
        double meanB = Arrays.stream(buffer).parallel().map(v -> (v) & 0xFF).reduce(Integer::sum).orElse(0)  / (double)buffer.length;
        double meanA = Arrays.stream(buffer).parallel().map(v -> (v >> 24) & 0xFF).reduce(Integer::sum).orElse(0)  / (double)buffer.length;
        
        Logger.infof("r=%f, g=%f, b=%f, a=%f", meanR, meanG, meanB, meanA);
        
        return new Color(meanR, meanG, meanB, meanA).deriveColor(0, 1, 1, 1);
    }

}
