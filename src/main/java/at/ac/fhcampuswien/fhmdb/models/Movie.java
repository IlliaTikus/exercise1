package at.ac.fhcampuswien.fhmdb.models;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class Movie {
    private String title;
    private String description;
    private List<Genre> Genres = new ArrayList<>();

    public Movie(String title, String description, Genre genre) {
        this.title = title;
        this.description = description;
        Genres.add(genre);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static List<Movie> initializeMovies() throws IOException, JSONException {
        List<Movie> movies = new ArrayList<>();


        movies.add(new Movie("Test", "Test", Genre.ACTION));
        movies.add(new Movie("2", "2", Genre.ANIMATION));
        movies.add(new Movie("3", "3", Genre.BIOGRAPHY));
        movies.add(new Movie("4", "4", Genre.COMEDY));
        return movies;
    }
}
