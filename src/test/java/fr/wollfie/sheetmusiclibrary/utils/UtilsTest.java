package fr.wollfie.sheetmusiclibrary.utils;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.number.OrderingComparison.*;
import static org.hamcrest.CoreMatchers.*;

import static fr.wollfie.sheetmusiclibrary.UsefulObjects.*;

public class UtilsTest {
    
    @Test void toRgbCodeReturnsCorrectColor() {
        assertThat(Utils.toRGBCode(new Color(1, 0, 0, 1)).toLowerCase(), is("#ff0000"));
        assertThat(Utils.toRGBCode(new Color(0, 1, 0, 1)).toLowerCase(), is("#00ff00"));
        assertThat(Utils.toRGBCode(new Color(0, 0, 1, 1)).toLowerCase(), is("#0000ff"));
    }
    
    @Test void stringRepeatsWorkAsDocumented() {
        assertThat(Utils.stringRepeat(8, "c"), is("cccccccc"));
        assertThat(Utils.stringRepeat(4, "ab"), is("abababab"));
        assertThat(Utils.stringRepeat(0, "ab"), is(""));
    }
}
