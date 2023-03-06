package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void initialized_movie_list_is_not_null(){
        List<Movie> movieList = Movie.initializeMovies();
        assertNotNull(movieList);
    }

    @Test
    void initialized_movie_list_is_empty_returns_false(){
        List<Movie> movieList = Movie.initializeMovies();
        assertFalse(movieList.isEmpty());
    }

}