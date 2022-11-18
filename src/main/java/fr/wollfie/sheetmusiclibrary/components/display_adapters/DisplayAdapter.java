package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.controllers.editable_field.UIMode;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/** Transforms a {@link Metadata} into its graphic representation */
public abstract class DisplayAdapter<M extends Metadata> {

    private static final int SPACING = 10;

    /** @return the graphic representation of the given metadata {@code m}*/
    public abstract Node getItemRepresentation(M m);
    
    /** @return the page representation of the given metadata {@code m} */
    public abstract MetadataPage<M> getPageRepresentation(M m, ObjectProperty<UIMode> uiModeProperty);

    /**
     * @return a container which would contain the attached type {@link M} in a list fashion
     */
    public Pane getListContainer() {
        FlowPane pane = new FlowPane();
        pane.setHgap(SPACING);
        pane.setVgap(SPACING);
        pane.setMaxWidth(Double.MAX_VALUE);
        
        return pane; 
    }
}
