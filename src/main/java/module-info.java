module fr.wollfie.sheetmusiclibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.fontawesome5;

    requires com.fasterxml.jackson.databind;
    requires com.google.common;

    exports fr.wollfie.sheetmusiclibrary;
    exports fr.wollfie.sheetmusiclibrary.controllers;
    exports fr.wollfie.sheetmusiclibrary.dto;
    exports fr.wollfie.sheetmusiclibrary.theme;
    
    opens fr.wollfie.sheetmusiclibrary.dto to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.io.metadata to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.controllers to javafx.fxml;
}