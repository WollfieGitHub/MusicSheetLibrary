module fr.wollfie.sheetmusiclibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.core;

    requires org.json;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;

    exports fr.wollfie.sheetmusiclibrary;
    exports fr.wollfie.sheetmusiclibrary.controllers;
    exports fr.wollfie.sheetmusiclibrary.dto;
    exports fr.wollfie.sheetmusiclibrary.theme;
    exports fr.wollfie.sheetmusiclibrary.io.network;
    
    opens fr.wollfie.sheetmusiclibrary.dto to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.io.metadata to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.controllers to javafx.fxml;
    opens fr.wollfie.sheetmusiclibrary to javafx.fxml;
}