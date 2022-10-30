package fr.wollfie.sheetmusiclibrary;

import fr.wollfie.sheetmusiclibrary.controllers.SearchBar;
import fr.wollfie.sheetmusiclibrary.controllers.ThemedButton;
import fr.wollfie.sheetmusiclibrary.library.SearchEngine;
import fr.wollfie.sheetmusiclibrary.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.util.*;
import java.util.function.Consumer;

public class IconChoice extends VBox {

    private static final Map<Character, List<Ikon>> ALL_ICONS = new HashMap<>();
    static {
        ALL_ICONS.put('A', Arrays.asList(MaterialDesignA.values()));
        ALL_ICONS.put('B', Arrays.asList(MaterialDesignB.values()));
        ALL_ICONS.put('C', Arrays.asList(MaterialDesignC.values()));
        ALL_ICONS.put('D', Arrays.asList(MaterialDesignD.values()));
        ALL_ICONS.put('E', Arrays.asList(MaterialDesignE.values()));
        ALL_ICONS.put('F', Arrays.asList(MaterialDesignF.values()));
        ALL_ICONS.put('G', Arrays.asList(MaterialDesignG.values()));
        ALL_ICONS.put('H', Arrays.asList(MaterialDesignH.values()));
        ALL_ICONS.put('I', Arrays.asList(MaterialDesignI.values()));
        ALL_ICONS.put('J', Arrays.asList(MaterialDesignJ.values()));
        ALL_ICONS.put('K', Arrays.asList(MaterialDesignK.values()));
        ALL_ICONS.put('L', Arrays.asList(MaterialDesignL.values()));
        ALL_ICONS.put('M', Arrays.asList(MaterialDesignM.values()));
        ALL_ICONS.put('N', Arrays.asList(MaterialDesignN.values()));
        ALL_ICONS.put('O', Arrays.asList(MaterialDesignO.values()));
        ALL_ICONS.put('P', Arrays.asList(MaterialDesignP.values()));
        ALL_ICONS.put('Q', Arrays.asList(MaterialDesignQ.values()));
        ALL_ICONS.put('R', Arrays.asList(MaterialDesignR.values()));
        ALL_ICONS.put('S', Arrays.asList(MaterialDesignS.values()));
        ALL_ICONS.put('T', Arrays.asList(MaterialDesignT.values()));
        ALL_ICONS.put('U', Arrays.asList(MaterialDesignU.values()));
        ALL_ICONS.put('V', Arrays.asList(MaterialDesignV.values()));
        ALL_ICONS.put('W', Arrays.asList(MaterialDesignW.values()));
        ALL_ICONS.put('X', Arrays.asList(MaterialDesignX.values()));
        ALL_ICONS.put('Y', Arrays.asList(MaterialDesignY.values()));
        ALL_ICONS.put('Z', Arrays.asList(MaterialDesignZ.values()));
    }
    
    public IconChoice(Consumer<FontIcon> chosenFontIcon, int maxIconChoice) {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(5);

        SearchBar searchBar = new SearchBar();
        FlowPane choicePane = new FlowPane();
        choicePane.setHgap(2);
        choicePane.setVgap(2);
        
        searchBar.searchTextProperty().addListener(observable -> {
            String searchText = searchBar.searchTextProperty().get();
            if (searchText.length() == 0) { return; }
            
            choicePane.getChildren().setAll(
                SearchEngine.updatePropositionsAccordingTo(
                        searchText,
                        ALL_ICONS.get(Character.toUpperCase(searchText.charAt(0))),
                        icon -> Collections.singletonList(icon.getDescription()),
                        maxIconChoice
                        
                ).parallelStream().map(ikon -> {
                    FontIcon fontIcon = new FontIcon(ikon);
                    fontIcon.setIconSize(40);
                    ThemedButton button = new ThemedButton(null, fontIcon, Theme.Category.Primary);
                    button.setOnAction(e -> chosenFontIcon.accept(fontIcon));
                    return button;
                }).toList()
            );
        });
        
        getChildren().addAll(searchBar, choicePane);
    }
}
