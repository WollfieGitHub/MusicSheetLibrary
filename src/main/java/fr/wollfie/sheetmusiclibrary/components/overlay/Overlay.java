package fr.wollfie.sheetmusiclibrary.components.overlay;


import javafx.scene.Node;

public interface Overlay {
    
    void mounted();
    
    Node getNode();
}
