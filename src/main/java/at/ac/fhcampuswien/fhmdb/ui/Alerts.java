package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

    public static void showAlert(AlertType type, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(String.format("%s Dialog", type.toString()));
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
