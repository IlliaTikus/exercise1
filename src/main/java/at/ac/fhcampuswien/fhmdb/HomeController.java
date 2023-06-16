package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.Alerts;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox yearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    @FXML
    public MenuItem watchlist;

    public List<Movie> allMovies;

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    private SortState state;

    public HomeController() {
        state = new SortStateStart();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allMovies = MovieAPI.initializeMovies();
        } catch (MovieApiException e) {
            allMovies = new ArrayList<>();
            Alerts.showAlert(AlertType.ERROR, "An error occured while initializing movies:\n"+e.getMessage());
        }
        resetBtn.setCursor(Cursor.HAND);
        sortBtn.setCursor(Cursor.HAND);
        searchBtn.setCursor(Cursor.HAND);

        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        allMovies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .distinct()
                .sorted()
                .forEach(genre -> genreComboBox.getItems().add(genre));

        yearComboBox.setPromptText("Filter by Release Year");
        allMovies.stream()
                .map(movie -> movie.getYear())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(year -> yearComboBox.getItems().add(year));

        ratingComboBox.setPromptText("Filter by Rating");
        allMovies.stream()
                .map(movie -> movie.getRating())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(rating -> ratingComboBox.getItems().add(rating));


        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            state.next(this);
            state.sortMovies(observableMovies);
        });

        searchBtn.setOnAction(actionEvent -> {
            String query = searchField.getText().isBlank() ? null : searchField.getText();
            String genre = genreComboBox.getValue() != null ? genreComboBox.getValue().toString().toUpperCase() : null;
            String releaseYear = yearComboBox.getValue() != null ? yearComboBox.getValue().toString() : null;
            String rating = ratingComboBox.getValue() != null ? ratingComboBox.getValue().toString() : null;
            if(query == null && genre == null && releaseYear == null && rating == null) return;

            List<Movie> filtered = null;
            try {
                filtered = MovieAPI.getMovieList(query, genre, releaseYear, rating);
            } catch (MovieApiException e) {
                Alerts.showAlert(AlertType.ERROR,
                        "An error occured while trying to fetch movie list:\n"+e.getMessage());
                return;
            }
            observableMovies.clear();
            observableMovies.addAll(filtered);
            state.sortMovies(observableMovies);
            movieListView.refresh();
        });

        resetBtn.setOnAction((actionEvent -> {
            resetView(allMovies);
            genreComboBox.getSelectionModel().clearSelection();
            yearComboBox.getSelectionModel().clearSelection();
            ratingComboBox.getSelectionModel().clearSelection();
            searchField.clear();
        }));

    }

    public void filterByGenre(List<Movie> allMovies, Genre genre) {
        for(Movie movie : allMovies) {
            if(movie.getGenres().contains(genre)) {
                observableMovies.add(movie);
            }
        }
    }

    public void resetView(List <Movie> allMovies) {
        observableMovies.clear();
        observableMovies.addAll(allMovies);
    }

    public List<Movie> filterByQuery(List<Movie> unfiltered, String query){
        List<Movie> filtered = new ArrayList<>();
        for(Movie m : unfiltered){
            if(m.containsSubstring(query)) filtered.add(m);
        }
        return filtered;
    }

    public String getMostPopularActor(List<Movie> movies) {
        Map<String, Long> actors = movies.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry = actors.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        if (!maxEntry.isPresent())
            return null;
        return maxEntry.get().getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        Optional<String> title = movies.stream()
                .map(movie -> movie.getTitle())
                .max(Comparator.comparingInt(i -> i.length()));

        if (title.isPresent())
            return title.get().length();
        else
            return 0;
    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream()
                .flatMap(movie -> movie.getDirectors().stream())
                .filter(dir -> dir.equals(director)).count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        return movies.stream()
                .filter(movie -> movie.getYear() >= startYear && movie.getYear() <= endYear)
                .collect(Collectors.toList());
    }

    public void onWatchlist(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) resetBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("Watchlist");
        stage.setScene(scene);
    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) resetBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("Watchlist");
        stage.setScene(scene);
    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) -> {
        try {
            new WatchlistRepository().addToWatchlist(new WatchlistEntity
                    (String.valueOf(clickedItem.getId()), clickedItem.getTitle(), clickedItem.getDescription(),
                            clickedItem.getGenres(), clickedItem.getYear(), clickedItem.getImgUrl(),
                            clickedItem.getLengthInMinutes(), clickedItem.getRating()));
        }catch (DatabaseException e){
            Alerts.showAlert(AlertType.WARNING, e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    };

    public void setState(SortState state){
        this.state = state;
    }

}