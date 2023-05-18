package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie implements Comparable<Movie>{

    private String id;
    private String title;
    private String description;
    private List<Genre> genres;
    private String genresstring;
    private int releaseYear;
    private double rating;
    private List<String> mainCast;
    private List<String> directors;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> writers;

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
        this.releaseYear = Integer.parseInt(year);
        this.rating = Double.parseDouble(rating);
        this.mainCast = mainCast;
        this.directors = directors;
    }

    public Movie(String title, String description, String genres, int year, double rating) {
        this.title = title;
        this.description = description;
        this.genresstring = genres;
        this.releaseYear = year;
        this.rating = rating;
    }

    public Movie(String title, String description, List<Genre> genres, String year, String rating,
                 List<String> mainCast, List<String> directors, String id, String imgUrl,
                 String lengthInMinutes, List<String> writers) {
        this(title, description, genres, year, rating, mainCast, directors);
        this.id = id;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = Integer.parseInt(lengthInMinutes);
        this.writers = writers;
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

    public int getYear() { return releaseYear; }

    public double getRating() { return rating; }

    public List<String> getActors() { return mainCast; }

    public List<String> getDirectors() { return directors; }

    public String getId() { return id; }

    public String getImgUrl() { return imgUrl; }

    public int getLengthInMinutes() { return lengthInMinutes; }

    public String getGenresstring() {
        return genresstring;
    }

    public List<String> getWriters() { return writers; }

    public boolean containsSubstring(String substring){
        return String.format("%s %s", title, description).toLowerCase().contains(substring.toLowerCase());
    }

    @Override
    public int compareTo(final Movie movie)
    {
        return this.getTitle().compareTo(movie.getTitle());
    }
}

