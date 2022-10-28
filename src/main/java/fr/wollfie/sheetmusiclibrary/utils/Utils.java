package fr.wollfie.sheetmusiclibrary.utils;

import javafx.scene.paint.Color;

import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
    
    public static String stringRepeat(int n, String s) {
        return new String(new char[n]).replace("\0", s);
    }
    
    public static <T> Predicate<T> lessThan(int n, Function<T, Integer> keyExtractor) {
        return t -> keyExtractor.apply(t) <= n;
    }
}
