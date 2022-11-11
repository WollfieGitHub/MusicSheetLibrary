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

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ),
                (int)( color.getOpacity() * 255) );
    }
    
    public static Color interpolate(Color c1, Color c2) {
        return new Color(
                (c1.getRed() + c2.getRed()) / 2.0,
                (c1.getGreen() + c2.getGreen()) / 2.0,
                (c1.getBlue() + c2.getBlue()) / 2.0,
                (c1.getOpacity() + c2.getOpacity()) / 2.0
        );
    }
    
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
