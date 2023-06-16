package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;

public class SortStateAscending implements SortState{

    @Override
    public void next(HomeController controller) {
        controller.setState(new SortStateDescending());
    }

    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        Collections.sort(movies);
    }

}
