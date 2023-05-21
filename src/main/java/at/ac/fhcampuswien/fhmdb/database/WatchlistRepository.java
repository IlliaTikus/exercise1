package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        this.dao = Database.getInstance().getWatchlistDao();
    }

    public void addToWatchlist(WatchlistEntity movie) throws DatabaseException {
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        List<WatchlistEntity> existingMovies;
        try {
            queryBuilder.where().eq("apiId", movie.getApiId());
            existingMovies = dao.query(queryBuilder.prepare());
        }catch (SQLException e){
            throw new DatabaseException("Failed to query from DB on: addToWatchlist", e);
        }

        if (existingMovies.isEmpty()) {
            try {
                dao.create(movie);
            }catch (SQLException e){
                throw new DatabaseException("Failed to create DB entries!", e);
            }
        } else {
           throw new DatabaseException("Movie with apiId " + movie.getApiId() + " already exists in the watchlist.");
        }
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws DatabaseException{
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        List<WatchlistEntity> existingMovies;
        try {
            queryBuilder.where().eq("apiId", movie.getApiId());
            existingMovies = dao.query(queryBuilder.prepare());
        }catch(SQLException e){
            throw new DatabaseException("Failed to query from DB on: removeFromWatchlist", e);
        }

        if (!existingMovies.isEmpty()) {
            try {
                dao.delete(existingMovies);
            }catch (SQLException e){
                throw new DatabaseException("Failed to delete DB entries!", e);
            }
        } else {
            throw new DatabaseException("Movie with id " + movie.getId() + " does not exist in the watchlist.");
        }
    }

    public List<WatchlistEntity> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query watchlist members!", e);
        }
    }
}
