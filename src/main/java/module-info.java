module gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires org.jsoup;
    requires org.apache.jena.ext.com.google;
    requires java.desktop;
    opens entity to javafx.base;
    opens gui to javafx.fxml;
    exports gui;
    exports gui.controller;
    opens gui.controller to javafx.fxml;
}