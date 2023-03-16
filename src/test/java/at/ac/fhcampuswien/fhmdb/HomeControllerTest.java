package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    public void filter_by_genre_returns_true (){
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

    @Test
    public void reset_return_true () {
        //given
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "1994", Arrays.asList(Genre.DRAMA)));
        allMovies.add(new Movie("The Godfather", "1972", Arrays.asList(Genre.CRIME, Genre.DRAMA)));
        allMovies.add(new Movie("The Dark Knight", "2008", Arrays.asList(Genre.ACTION, Genre.CRIME)));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "2001", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)));

        HomeController controller = new HomeController();

        //when
        controller.filterByGenre(allMovies, Genre.DRAMA);
        controller.resetView(allMovies);
        //then
        for (int i = 0; i < 3; i++) {
            assertTrue(controller.observableMovies.contains(allMovies.get(i)));
        }
    }

    @Test
    public void filter_by_query_returns_empty_list_when_not_found_in_any_movie(){
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA)));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA)));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME)));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)));
        HomeController controller = new HomeController();
        String query = "test";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        assertEquals(0, filtered.size());
    }

    @Test
    public void filter_by_query_returns_non_empty_list_when_found(){
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA)));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA)));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME)));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)));
        HomeController controller = new HomeController();
        String query = "foo";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        assertTrue(filtered.size() > 0);
    }

    @Test
    public void filter_by_query_returns_filtered_list_with_all_movies_containing_query(){
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA)));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA)));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME)));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)));
        HomeController controller = new HomeController();
        String query = "foo";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        for(Movie m : filtered){
            assertTrue(m.containsSubstring(query));
        }
    }

}