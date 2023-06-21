package at.ac.fhcampuswien.fhmdb.observer;

import javafx.scene.control.Alert;

public interface Observer {
    void update(String message, Alert.AlertType type);
}
