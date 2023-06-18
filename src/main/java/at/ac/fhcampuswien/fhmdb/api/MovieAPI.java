package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
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

    private static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static List<Movie> getMovieList() throws MovieApiException {
        String baseUrl = "https://prog2.fh-campuswien.ac.at/movies";
        String url = new MovieAPIRequestBuilder(baseUrl)
                .build();

        return parseUrl(url);
    }

    public static List<Movie> getMovieList(String query, String genre, String releaseYear, String rating)
            throws MovieApiException {
        String baseUrl = "https://prog2.fh-campuswien.ac.at/movies";
        String url = new MovieAPIRequestBuilder(baseUrl)
                .query(query)
                .genre(genre)
                .releaseYear(releaseYear)
                .ratingFrom(rating)
                .build();

        return parseUrl(url);
    }

    public static List<Movie> parseUrl(String url) {
        String response = "";
        try {
            response = run(url);
        } catch (IOException e) {
            throw new MovieApiException("Request to API failed!", e);
        }

        Gson gson = new Gson();
        Movie[] moviesArray = gson.fromJson(response, Movie[].class);

        return new ArrayList<>(Arrays.asList(moviesArray));
    }

    public static void main(String[] args) throws IOException {
        getMovieList();
    }
}
