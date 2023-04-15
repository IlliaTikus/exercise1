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

    public static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static List<Movie> initializeMovies() throws IOException {
        String response = run("https://prog2.fh-campuswien.ac.at/movies");

        Gson gson = new Gson();
        Movie[] moviesArray = gson.fromJson(response, Movie[].class);

        return new ArrayList<>(Arrays.asList(moviesArray));
    }

    public static List<Movie> getMovieList(String query, String genre, Integer releaseYear, Double rating) throws IOException {
        StringBuilder url = new StringBuilder("https://prog2.fh-campuswien.ac.at/movies");
        boolean params = false;
        if(query!=null){
            url.append("?query=").append(query);
            params = true;
        }
        if(genre!=null){
            url.append(params ? "&" : "?").append("genre=").append(genre);
            if(!params) params = true;
        }
        if(releaseYear!=null){
            url.append(params ? "&" : "?").append("releaseYear=").append(releaseYear);
            if(!params) params = true;
        }
        if(rating!=null){
            url.append(params ? "&" : "?").append("ratingFrom=").append(rating);
            if(!params) params = true;
        }
        String response = run(url.toString());
        Gson gson = new Gson();
        Movie[] moviesArray = gson.fromJson(response, Movie[].class);

        return new ArrayList<>(Arrays.asList(moviesArray));
    }

    public static void main(String[] args) throws IOException {
        initializeMovies();
    }

}
