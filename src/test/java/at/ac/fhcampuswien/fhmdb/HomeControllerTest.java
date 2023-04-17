package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeControllerTest {
/*
    @Test
    public void filter_by_genre_returns_true () throws IOException {
        //given
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "1994", Arrays.asList(Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Godfather", "1972", Arrays.asList(Genre.CRIME, Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Dark Knight", "2008", Arrays.asList(Genre.ACTION, Genre.CRIME), "1950", "9.5"));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "2001", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY), "1950", "9.5"));

        HomeController controller = new HomeController();

        //when
        controller.filterByGenre(allMovies, Genre.DRAMA);

        //then
        assertTrue(controller.observableMovies.contains(allMovies.get(0)));
        assertTrue(controller.observableMovies.contains(allMovies.get(1)));
    }

    @Test
    public void reset_return_true () throws IOException {
        //given
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "1994", Arrays.asList(Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Godfather", "1972", Arrays.asList(Genre.CRIME, Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Dark Knight", "2008", Arrays.asList(Genre.ACTION, Genre.CRIME), "1950", "9.5"));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "2001", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY), "1950", "9.5"));

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
    public void filter_by_query_returns_empty_list_when_not_found_in_any_movie() throws IOException {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME), "1950", "9.5"));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY), "1950", "9.5"));
        HomeController controller = new HomeController();
        String query = "test";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        assertEquals(0, filtered.size());
    }

    @Test
    public void filter_by_query_returns_non_empty_list_when_found() throws IOException {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME), "1950", "9.5"));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY), "1950", "9.5"));
        HomeController controller = new HomeController();
        String query = "foo";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        assertTrue(filtered.size() > 0);
    }

    @Test
    public void filter_by_query_returns_filtered_list_with_all_movies_containing_query() throws IOException {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA), "1950", "9.5"));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME), "1950", "9.5"));
        allMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Bar", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY), "1950", "9.5"));
        HomeController controller = new HomeController();
        String query = "foo";
        List<Movie> filtered = controller.filterByQuery(allMovies, query);
        for(Movie m : filtered){
            assertTrue(m.containsSubstring(query));
        }
    }*/

    @Test
    public void count_movies_from_returns_0_when_director_not_found_in_movie_list(){
        List<Movie> allMovies = new ArrayList<>();
        List<String> directorsWithSpielberg = Arrays.asList("Steven Spielberg", "Christopher Nolan");
        List<String> directorsNoSpielberg = List.of("Christopher Nolan");
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsNoSpielberg));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        long expected = 0;
        long actual = new HomeController().countMoviesFrom(allMovies, "Martin Scorcese");
        assertEquals(expected, actual);
    }

    @Test
    public void count_movies_from_returns_expected_count_when_director_found_in_movie_list(){
        List<Movie> allMovies = new ArrayList<>();
        List<String> directorsWithSpielberg = Arrays.asList("Steven Spielberg", "Christopher Nolan");
        List<String> directorsNoSpielberg = List.of("Christopher Nolan");
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsNoSpielberg));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        long expected = 2;
        long actual = new HomeController().countMoviesFrom(allMovies, "Steven Spielberg");
        assertEquals(expected, actual);
    }

    @Test
    public void get_movies_between_years_returns_empty_list_if_no_movies_released_between_given_years(){
        List<Movie> allMovies = new ArrayList<>();
        List<String> directorsWithSpielberg = Arrays.asList("Steven Spielberg", "Christopher Nolan");
        List<String> directorsNoSpielberg = List.of("Christopher Nolan");
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2002", "9.0", new ArrayList<>(), directorsNoSpielberg));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2004", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("Leon", "Foo", Arrays.asList(Genre.ACTION, Genre.FAMILY),
                "2006", "9.0", new ArrayList<>(), directorsNoSpielberg));
        List<Movie> actual = new HomeController().getMoviesBetweenYears(allMovies, 4000, 5000);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void get_movies_between_years_returns_filtered_list_with_movies_released_between_given_years(){
        List<Movie> allMovies = new ArrayList<>();
        List<String> directorsWithSpielberg = Arrays.asList("Steven Spielberg", "Christopher Nolan");
        List<String> directorsNoSpielberg = List.of("Christopher Nolan");
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2002", "9.0", new ArrayList<>(), directorsNoSpielberg));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2004", "9.0", new ArrayList<>(), directorsWithSpielberg));
        allMovies.add(new Movie("Leon", "Foo", Arrays.asList(Genre.ACTION, Genre.FAMILY),
                "2006", "9.0", new ArrayList<>(), directorsNoSpielberg));
        List<Movie> expected = new ArrayList<>();
        expected.add(allMovies.get(1));
        expected.add(allMovies.get(2));
        List<Movie> actual = new HomeController().getMoviesBetweenYears(allMovies, 2001, 2005);
        assertEquals(expected, actual);
    }

    @Test
    public void get_most_popular_actors_returns_actor () {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", Arrays.asList("Marlon Brando", "Al Pacino"), Arrays.asList("Francis Ford Coppola")));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2000", "9.0", Arrays.asList("Heath Ledger", "Al Pacino"), Arrays.asList("Frank Darabont")));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2000", "9.0", Arrays.asList("Ralph Fiennes", "Ben Kingsley"), Arrays.asList("Christopher Nolan")));

        String expected = "Al Pacino";
        String actual = new HomeController().getMostPopularActor(allMovies);
        assertEquals(expected, actual);
    }

    @Test
    public void get_longest_movie_title_returns_length () {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.add(new Movie("The Shawshank Redemption", "Foo", Arrays.asList(Genre.DRAMA),
                "2000", "9.0", Arrays.asList("Marlon Brando", "Al Pacino"), Arrays.asList("Francis Ford Coppola")));
        allMovies.add(new Movie("The Godfather", "Bar", Arrays.asList(Genre.CRIME, Genre.DRAMA),
                "2000", "9.0", Arrays.asList("Heath Ledger", "Al Pacino"), Arrays.asList("Frank Darabont")));
        allMovies.add(new Movie("The Dark Knight", "Foo", Arrays.asList(Genre.ACTION, Genre.CRIME),
                "2000", "9.0", Arrays.asList("Ralph Fiennes", "Ben Kingsley"), Arrays.asList("Christopher Nolan")));

        int expected = allMovies.get(0).getTitle().length();
        int actual = new HomeController().getLongestMovieTitle(allMovies);
        assertEquals(expected, actual);
    }
}