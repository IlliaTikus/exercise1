package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Test
    void test_Junit () throws IOException {
        //given
        int a = 1;
        //when
        int actual = Movie.initializeMovies().size();

        //then
        assertEquals(a, actual);
    }

}