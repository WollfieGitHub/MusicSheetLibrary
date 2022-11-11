package fr.wollfie.sheetmusiclibrary.components.music_library_display.creator.prompts;

import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.scene.Node;
import javafx.util.Pair;

import java.util.function.Consumer;

public abstract class PairPrompt<L, R> extends ValuePrompt<Tuple<L, R>>  {


    /**
     * Prompt that fuses two prompts
     * @param leftPrompt The left prompt
     * @param rightPrompt The right prompt
     */
    public PairPrompt(ValuePrompt<L> leftPrompt, ValuePrompt<R> rightPrompt) {
        super(leftPrompt.prompt + " - " + rightPrompt.prompt, pair -> {
            leftPrompt.callback.accept(pair.left());
            rightPrompt.callback.accept(pair.right());
        });
    }

}
