module com.example.nf28_td {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;

    opens com.example.nf28_td to javafx.fxml;
    exports com.example.nf28_td;
    exports com.example.nf28_td.Controller;
    opens com.example.nf28_td.Controller to javafx.fxml;
}