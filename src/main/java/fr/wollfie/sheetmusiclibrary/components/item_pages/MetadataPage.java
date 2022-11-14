package fr.wollfie.sheetmusiclibrary.components.item_pages;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public abstract class MetadataPage<M extends Metadata> extends StackPane {

    protected final M item;
    
    /**
     * Builds a page to display the given item's info and possibly edit
     * their value
     * @param item The item to display as a content
     */
    public MetadataPage(M item) {
        this.item = item;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(20);
        
        this.initComponent(vBox);
        setAlignment(Pos.CENTER);
        getChildren().setAll(vBox);
    }

    /**
     * Decorate this component with needed info, accessing {@link MetadataPage#item} to get 
     * the necessary data
     */
    protected abstract void initComponent(VBox vBox);
}
