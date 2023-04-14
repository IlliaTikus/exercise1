package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {
    private static final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
//                .addHeader("Custom-Header", "header-value")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        initializeMovies();
    }

    public static List<Movie> initializeMovies() throws IOException {
        MovieAPI example = new MovieAPI();
        String response = null;

        response = example.run("http://localhost:8080/movies");

        System.out.println(response);

        Gson gson = new Gson();

        Movie[] moviesArray = gson.fromJson(response, Movie[].class);
        List<Movie> movies = new ArrayList<>(Arrays.asList(moviesArray));

        System.out.println(movies);

        return movies;
    }
}
