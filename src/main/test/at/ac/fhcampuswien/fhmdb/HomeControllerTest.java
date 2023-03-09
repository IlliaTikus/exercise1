package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    void filter_by_genre_returns_true (){
        //given
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "1994", Arrays.asList(Genre.DRAMA)));
        allMovies.add(new Movie("The Godfather", "1972", Arrays.asList(Genre.CRIME, Genre.DRAMA)));
        allMovies.add(new Movie("The Dark Knight", "2008", Arrays.asList(Genre.ACTION, Genre.CRIME)));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "2001", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)));

        HomeController controller = new HomeController();

        //when
        controller.filterByGenre(allMovies, Genre.DRAMA);

        //then
        assertTrue(controller.observableMovies.contains(allMovies.get(0)));
        assertTrue(controller.observableMovies.contains(allMovies.get(1)));
    }

}