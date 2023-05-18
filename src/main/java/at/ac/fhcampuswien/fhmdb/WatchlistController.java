package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class WatchlistController implements Initializable {

    @FXML
    public JFXListView movieListView;

    public List<Movie> allMovies = new ArrayList<>();

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<WatchlistEntity> watchlist = new WatchlistRepository().getAll();
            for (int i = 0; i < watchlist.size(); i++) {
                Movie movie = new Movie(watchlist.get(i).getTitle(), watchlist.get(i).getDescription(),
                        watchlist.get(i).getGenres(), watchlist.get(i).getReleaseYear(), watchlist.get(i).getRating());
                allMovies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        observableMovies.addAll(allMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCellWatchlist(onRemoveFromWatchlistClicked)); // use custom cell factory to display data
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

    private final ClickEventHandler onRemoveFromWatchlistClicked = (clickedItem) -> {
        new WatchlistRepository().removeFromWatchlist(new WatchlistEntity
                (clickedItem.getId(),clickedItem.getTitle(),clickedItem.getDescription(),
                        clickedItem.getGenres(),clickedItem.getYear(), clickedItem.getImgUrl(), clickedItem.getLengthInMinutes(), clickedItem.getRating()));
    };
}
