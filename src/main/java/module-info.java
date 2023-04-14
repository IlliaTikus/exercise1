module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires org.json;
    requires okhttp3;
    requires com.google.gson;

    opens at.ac.fhcampuswien.fhmdb;
    opens at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
}