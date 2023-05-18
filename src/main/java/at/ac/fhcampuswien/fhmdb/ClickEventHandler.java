package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.sql.SQLException;

@FunctionalInterface
public interface ClickEventHandler {
    void onClick(Movie movie) throws SQLException;
}
