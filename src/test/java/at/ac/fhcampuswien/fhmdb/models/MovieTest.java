package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    public void initialized_movie_list_is_not_null(){
        List<Movie> movieList = null;
        try {
            movieList = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(movieList);
    }

    @Test
    public void initialized_movie_list_is_empty_returns_false(){
        List<Movie> movieList = null;
        try {
            movieList = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertFalse(movieList.isEmpty());
    }

    @Test
    public void movie_contains_substring_returns_true_if_exists_in_title(){
        List<Genre> genres = new LinkedList<>();
        genres.add(Genre.ADVENTURE);
        Movie movie = new Movie("Test Movie",
                "This is a great film about great people doing great things", genres);
        String substring = "test";
        boolean actual = movie.containsSubstring(substring);
        assertTrue(actual);
    }

    @Test
    public void movie_contains_substring_returns_true_if_exists_in_description(){
        List<Genre> genres = new LinkedList<>();
        genres.add(Genre.ADVENTURE);
        Movie movie = new Movie("Test Movie",
                "This is a great film about great people doing great things", genres);
        String substring = "great";
        boolean actual = movie.containsSubstring(substring);
        assertTrue(actual);
    }

    @Test
    public void movie_contains_substring_returns_false_if_doesnt_exist(){
        List<Genre> genres = new LinkedList<>();
        genres.add(Genre.ADVENTURE);
        Movie movie = new Movie("Test Movie",
                "This is a great film about great people doing great things", genres);
        String substring = "adventure";
        boolean actual = movie.containsSubstring(substring);
        assertFalse(actual);
    }

    @Test
    public void sort_movie_list_ascending_returns_true(){
//        ARRANGE
        List<Movie> movieList = null;
        try {
            movieList = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        ACT
        Collections.sort(movieList);
//        ASSERT
        for (int idx = 1; idx < movieList.size() - 2; idx++ ) {
            Movie currentMovie = movieList.get(idx);

            assertTrue(isSorted(currentMovie.getTitle(), movieList.get(idx + 1).getTitle(), true));
            assertTrue(isSorted(movieList.get(idx - 1).getTitle(), currentMovie.getTitle(), true));
        }
    }

    @Test
    public void sort_movie_list_descending_returns_true(){
//        ARRANGE
        List<Movie> movieList = null;
        try {
            movieList = MovieAPI.initializeMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        ACT
        Collections.sort(movieList);
        Collections.reverse(movieList);
//        ASSERT
        for (int idx = 1; idx < movieList.size() - 2; idx++ ) {
            Movie currentMovie = movieList.get(idx);

            assertTrue(isSorted(currentMovie.getTitle(), movieList.get(idx + 1).getTitle(), false));
            assertTrue(isSorted(movieList.get(idx - 1).getTitle(), currentMovie.getTitle(), false));
        }
    }

    private boolean isSorted(String firstTitle, String secondTitle, boolean isAscending) {
        if(firstTitle.equalsIgnoreCase(secondTitle)) {
            return true;
        }

        char[] firstTitleArr = firstTitle.toLowerCase().toCharArray();
        char[] secondTitleArr = secondTitle.toLowerCase().toCharArray();

        int iterationLength = Math.min(firstTitle.length(), secondTitle.length());

        for(int idx = 0; idx < iterationLength; idx++) {
            if((isAscending && firstTitleArr[idx] < secondTitleArr[idx]) || (!isAscending && firstTitleArr[idx] > secondTitleArr[idx])) {
                return true;
            }
        }

        return isAscending && firstTitleArr.length < secondTitleArr.length || !isAscending && firstTitleArr.length > secondTitleArr.length;
    }
}