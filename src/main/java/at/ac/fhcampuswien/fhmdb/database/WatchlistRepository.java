package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository(){
        this.dao = Database.getInstance().getWatchlistDao();
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        dao.create(movieToWatchlist(movie));
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        dao.delete(movieToWatchlist(movie));
    }

    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }

    private WatchlistEntity movieToWatchlist(WatchlistEntity movie){ // Todo Add attributes for movies
        return new WatchlistEntity();
    }
}
