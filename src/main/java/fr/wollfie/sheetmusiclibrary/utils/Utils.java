package fr.wollfie.sheetmusiclibrary.utils;

import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       COLOR TO STING                                 ||
// ||                                                                                      ||
// \\======================================================================================//
    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ),
                (int)( color.getOpacity() * 255) );
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       INTERPOLATION                                  ||
// ||                                                                                      ||
// \\======================================================================================//

    public static Color interpolate(Color c1, Color c2) { 
        return interpolate(c1, c2, 0.5);
    }
    
    public static Color interpolate(Color c1, Color c2, double ratio) {
        return new Color(
                c1.getRed() * ratio + c2.getRed() * (1-ratio),
                c1.getGreen() * ratio + c2.getGreen() * (1-ratio),
                c1.getBlue() * ratio + c2.getBlue() * (1-ratio),
                c1.getOpacity() * ratio + c2.getOpacity() * (1-ratio)
        );
    }

    /**
     * Generate a css linear-gradient instruction with
     * @param direction The direction of the gradient e.g. <code language="CSS">to top</code>
     * @param c1 Starting color
     * @param c2 End color
     * @param curve The curve to follow while applying the gradient
     * @param samples The number of samples to take
     * @return A linear gradient string
     */
    public static String generateGradient(String direction, Color c1, Color c2, Curve curve, int samples) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("linear-gradient(%s, ", direction));

        double position, ratio;
        String positionPercent, resultingColor;
        for (int i = 0; i < samples; i++) {
            position = i / (double)samples;
            positionPercent = (position*100) + "%";
            
            ratio = curve.apply(position);
            resultingColor = toRGBCode(interpolate(c1, c2, ratio));
            stringBuilder.append(String.format("%s %s,", resultingColor, positionPercent));
        }
        stringBuilder.append(String.format("%s %s)", toRGBCode(c2), "100%"));
        
        return stringBuilder.toString();
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       UTILS                                          ||
// ||                                                                                      ||
// \\======================================================================================//
    
    public static <T> Callback<T> onlyOnNonNull(Callback<T> callback) {
        return t -> {
            if (t != null) { callback.accept(t); }
        };
    }
    
    public static String stringRepeat(int n, String s) {
        return new String(new char[n]).replace("\0", s);
    }
    
    public static <T> Predicate<T> moreThan(double n, Function<T, Double> keyExtractor) {
        return t -> keyExtractor.apply(t) >= n;
    }
    
    public static <T> InvalidationListener onChange(Runnable runnable) {
        return observable -> runnable.run();
    } 
    
    public static EventHandler<KeyEvent> onKeyTyped(KeyCode keyCode, Runnable runnable) {
        return e -> { if (e.getCode() == keyCode) { runnable.run(); e.consume(); } };
    }
    
    public static String sanitized(String s) {
        return s.replaceAll("[^\\S\\r\\n]", " ")
                .replaceAll( ",", "")
                .split("[\\r|\\n]")[0];
    }
}
