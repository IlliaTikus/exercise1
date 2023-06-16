package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public interface SortState {

    void next(HomeController controller);
    void sortMovies(ObservableList<Movie> movies);

}
