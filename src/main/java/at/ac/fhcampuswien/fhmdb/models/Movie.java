package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie implements Comparable<Movie>{

    private String title;
    private String description;
    private List<Genre> genres;
    private String year;
    private String rating;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public Movie(String title, String description, List<Genre> genres, String year, String rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.year = year;
        this.rating = rating;
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

    public String getYear() { return year; }

    public String getRating() { return rating; }

    public boolean containsSubstring(String substring){
        return String.format("%s %s", title, description).toLowerCase().contains(substring.toLowerCase());
    }

    @Override
    public int compareTo(final Movie movie)
    {
        return this.getTitle().compareTo(movie.getTitle());
    }
}

