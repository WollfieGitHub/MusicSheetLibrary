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
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.pdfbox;
    
    exports fr.wollfie.sheetmusiclibrary;
    exports fr.wollfie.sheetmusiclibrary.controllers;
    exports fr.wollfie.sheetmusiclibrary.components;
    exports fr.wollfie.sheetmusiclibrary.dto;
    exports fr.wollfie.sheetmusiclibrary.theme;
    exports fr.wollfie.sheetmusiclibrary.io.network;
    exports fr.wollfie.sheetmusiclibrary.library;
    exports fr.wollfie.sheetmusiclibrary.dto.files;
    exports fr.wollfie.sheetmusiclibrary.utils;

    opens fr.wollfie.sheetmusiclibrary.dto to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.io.metadata to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.library to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary to javafx.fxml;
    opens fr.wollfie.sheetmusiclibrary.controllers to javafx.fxml;
    opens fr.wollfie.sheetmusiclibrary.dto.files to com.fasterxml.jackson.databind;
    opens fr.wollfie.sheetmusiclibrary.utils to com.fasterxml.jackson.databind;
    exports fr.wollfie.sheetmusiclibrary.controllers.editable_field;
    opens fr.wollfie.sheetmusiclibrary.controllers.editable_field to javafx.fxml;
}