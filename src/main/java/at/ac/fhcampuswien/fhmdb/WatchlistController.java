package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCellWatchlist;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class WatchlistController implements Initializable {

    @FXML
    public JFXListView movieListView;

    public List<Movie> allMovies;

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allMovies = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        observableMovies.addAll(allMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCellWatchlist()); // use custom cell factory to display data
    }

    public void onWatchlist(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) movieListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("Watchlist");
        stage.setScene(scene);
    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) movieListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("Watchlist");
        stage.setScene(scene);
    }
}
