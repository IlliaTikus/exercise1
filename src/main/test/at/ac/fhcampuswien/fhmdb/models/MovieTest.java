package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.util.Collections;
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

    @Test
    void sort_movie_list_ascending_returns_true(){
//        ARRANGE
        List<Movie> movieList = Movie.initializeMovies();

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
    void sort_movie_list_descending_returns_true(){
//        ARRANGE
        List<Movie> movieList = Movie.initializeMovies();

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