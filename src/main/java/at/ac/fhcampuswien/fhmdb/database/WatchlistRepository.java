package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository(){
        this.dao = Database.getInstance().getWatchlistDao();
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq("apiId", movie.getApiId());
        List<WatchlistEntity> existingMovies = dao.query(queryBuilder.prepare());

        if (existingMovies.isEmpty()) {
            dao.create(movie);
        } else {
           System.out.println("Movie with apiId " + movie.getApiId() + " already exists in the watchlist.");
        }
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq("apiId", movie.getApiId());
        List<WatchlistEntity> existingMovies = dao.query(queryBuilder.prepare());

        if (!existingMovies.isEmpty()) {
            dao.delete(existingMovies);
        } else {
            System.out.println("Movie with id " + movie.getId() + " does not exist in the watchlist.");
        }
    }

    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
