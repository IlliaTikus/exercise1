package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;

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

    public List<Movie> allMovies;

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public HomeController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allMovies = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resetBtn.setCursor(Cursor.HAND);
        sortBtn.setCursor(Cursor.HAND);
        searchBtn.setCursor(Cursor.HAND);

        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

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
            if(sortBtn.getText().equals("Sort (asc)")) {
                sortBtn.setText("Sort (desc)");
                Collections.sort(observableMovies);

            } else {
                sortBtn.setText("Sort (asc)");
                Collections.sort(observableMovies);
                Collections.reverse(observableMovies);
            }
        });

        searchBtn.setOnAction(actionEvent -> {
            String query = searchField.getText();
            List<Movie> filtered = null;
            if(!query.isBlank()){
                filtered = filterByQuery(allMovies, query);
            }
            if(genreComboBox.getValue() != null) {
                Genre genre = Genre.valueOf(genreComboBox.getValue().toString().toUpperCase());
                observableMovies.clear();
                filterByGenre((filtered != null ? filtered : allMovies), genre);
            }else if(filtered != null){
                observableMovies.clear();
                observableMovies.addAll(filtered);
            }
            movieListView.refresh();
        });

        resetBtn.setOnAction((actionEvent -> {
            resetView(allMovies);
            genreComboBox.valueProperty().set(null);
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
                .filter(title -> title != null)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry = actors.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        if (maxEntry.isPresent())
            return maxEntry.get().getKey();
        else return null;
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
        return 0L;
    }
}