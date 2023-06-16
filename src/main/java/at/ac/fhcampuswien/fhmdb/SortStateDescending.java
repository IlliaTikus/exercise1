package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;

public class SortStateDescending implements SortState{

    @Override
    public void next(HomeController controller) {
        controller.setState(new SortStateAscending());
    }

    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        Collections.sort(movies);
        Collections.reverse(movies);
    }

}
