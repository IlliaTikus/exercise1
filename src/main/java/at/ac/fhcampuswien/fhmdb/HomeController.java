package at.ac.fhcampuswien.fhmdb;

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

import java.net.URL;
import java.util.*;

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
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetBtn.setCursor(Cursor.HAND);
        sortBtn.setCursor(Cursor.HAND);
        searchBtn.setCursor(Cursor.HAND);

        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        //add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");

        //add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

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
        List<Movie> filtered = new LinkedList<>();
        for(Movie m : unfiltered){
            if(m.containsSubstring(query)) filtered.add(m);
        }
        return filtered;
    }

}