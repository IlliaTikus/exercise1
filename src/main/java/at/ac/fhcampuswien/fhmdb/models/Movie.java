package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie implements Comparable<Movie>{

    private String title;
    private String description;
    private List<Genre> genres;
    private String releaseYear;
    private String rating;
    private List<String> mainCast;
    private List<String> directors;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public Movie(String title, String description, List<Genre> genres, String year, String rating,
                 List<String> mainCast, List<String> directors) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = year;
        this.rating = rating;
        this.mainCast = mainCast;
        this.directors = directors;
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

    public String getYear() { return releaseYear; }

    public String getRating() { return rating; }

    public List<String> getActors() { return mainCast; }

    public List<String> getDirectors() { return directors; }

    public boolean containsSubstring(String substring){
        return String.format("%s %s", title, description).toLowerCase().contains(substring.toLowerCase());
    }

    @Override
    public int compareTo(final Movie movie)
    {
        return this.getTitle().compareTo(movie.getTitle());
    }
}

