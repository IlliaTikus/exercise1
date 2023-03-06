package at.ac.fhcampuswien.fhmdb.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import org.json.*;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        String path = "/at/ac/fhcampuswien/fhmdb/movies.json";
        JSONArray movieJSON = readMoviesJSONFromResources(path);

        for(int i = 0; i < movieJSON.length(); i++){
            JSONObject movieObject = movieJSON.getJSONObject(i);
            Movie movie = parseMovieFromJSONObject(movieObject);
            movies.add(movie);
        }

        return movies;
    }

    private static Movie parseMovieFromJSONObject(JSONObject movieObject){
        String name = movieObject.getString("name");
        String description;
        //some movies still have missing descriptions
        try{
            description = movieObject.getString("description");
        }catch (JSONException e){
            description = "";
        }
        JSONArray genreJSON = movieObject.getJSONArray("genre");
        List<Genre> genreList = new LinkedList<>();
        for(int j = 0; j < genreJSON.length(); j++){
            String genre = genreJSON.getString(j).toUpperCase();
            genreList.add(Genre.valueOf(genre));
        }
        return new Movie(name, description, genreList);
    }

    private static JSONArray readMoviesJSONFromResources(String fileName) {
        URL resource = Movie.class.getResource(fileName);
        if (resource == null)
            throw new IllegalArgumentException("File is not found!");
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(resource.getFile())));){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(fileContent.toString());
        return jsonObject.getJSONArray("movies");
    }

}
