package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class SortStateStart implements SortState {

    @Override
    public void next(HomeController controller) {
        controller.setState(new SortStateAscending());
    }

    @Override
    public void sortMovies(ObservableList<Movie> movies) {
    }

}
