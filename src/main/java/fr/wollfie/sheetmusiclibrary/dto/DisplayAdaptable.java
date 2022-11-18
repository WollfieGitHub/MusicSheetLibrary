package fr.wollfie.sheetmusiclibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.wollfie.sheetmusiclibrary.components.display_adapters.DisplayAdapter;
import javafx.scene.Node;

public interface DisplayAdaptable {
    /** @return the visual representation of the object */
    @JsonIgnore
    DisplayAdapter<?> getAdapter();
}
