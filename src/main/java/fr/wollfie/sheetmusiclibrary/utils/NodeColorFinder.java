package fr.wollfie.sheetmusiclibrary.utils;

import fr.wollfie.sheetmusiclibrary.exceptions.Errors;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            case ImageView imageView -> getAverageColorOf(imageView.getImage());
            default -> Errors.ERROR_COLOR;
        };
    }
    
    public static Color getAverageColorOf(Paint paint) {
        return switch (paint) {
            case Color color -> color;
            case ImagePattern pattern -> getAverageColorOf(pattern.getImage());
            
            case default -> Errors.ERROR_COLOR;
        };
    }

    private static final int SAMPLING = 2;
    
    public static Color getAverageColorOf(Image image) {
        PixelReader pr = image.getPixelReader();
        if (pr == null) { return Errors.ERROR_COLOR; }
        
        int nbPixels = (int) (image.getWidth() * image.getHeight());
        int[] buffer = new int[nbPixels];
        pr.getPixels(
                0, 0, (int)(image.getWidth()), (int)(image.getHeight()),
                PixelFormat.getIntArgbInstance(),
                buffer, 0, (int)image.getWidth()
        );
        
        double meanR = Arrays.stream(buffer).parallel().mapToLong(v -> (v >> 16) & 0xFF).sum() / (nbPixels * 255.0);
        double meanG = Arrays.stream(buffer).parallel().mapToLong(v -> (v >> 8) & 0xFF).sum() / (nbPixels * 255.0);
        double meanB = Arrays.stream(buffer).parallel().mapToLong(v -> (v) & 0xFF).sum() / (nbPixels * 255.0);
        double meanA = Arrays.stream(buffer).parallel().mapToLong(v -> (v >> 24) & 0xFF).sum() / (nbPixels * 255.0);
        
        return new Color(meanR, meanG, meanB, meanA).deriveColor(0, 1, 1, 1);
    }

}
