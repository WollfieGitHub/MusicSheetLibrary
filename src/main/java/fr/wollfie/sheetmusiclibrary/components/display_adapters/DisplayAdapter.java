package fr.wollfie.sheetmusiclibrary.components.display_adapters;

import fr.wollfie.sheetmusiclibrary.components.item_pages.MetadataPage;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public abstract class DisplayAdapter<M extends Metadata> {

    private static final int SPACING = 10;

    /** @return the graphic representation of the given metadata {@code m}*/
    public abstract Node getItemRepresentation(M m);
    
    /** @return the page representation of the given metadata {@code m} */
    public abstract MetadataPage<M> getPageRepresentation(M m);

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
