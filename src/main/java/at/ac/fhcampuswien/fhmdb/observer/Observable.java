package at.ac.fhcampuswien.fhmdb.observer;

import javafx.scene.control.Alert;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message, Alert.AlertType type);
}
