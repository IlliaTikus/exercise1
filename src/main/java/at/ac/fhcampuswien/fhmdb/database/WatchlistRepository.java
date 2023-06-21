package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.observer.Observable;
import at.ac.fhcampuswien.fhmdb.observer.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    private Dao<WatchlistEntity, Long> dao;
    private List<Observer> observers;

    private static WatchlistRepository instance;

    private WatchlistRepository() throws DatabaseException {
        this.dao = Database.getInstance().getWatchlistDao();
        observers = new ArrayList<>();
    }

    public void addToWatchlist(WatchlistEntity movie) throws DatabaseException {
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        List<WatchlistEntity> existingMovies;
        try {
            queryBuilder.where().eq("apiId", movie.getApiId());
            existingMovies = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query from DB on: addToWatchlist", e);
        }

        if (existingMovies.isEmpty()) {
            try {
                dao.create(movie);
                notifyObservers("Movie successfully added.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to create DB entries!", e);
            }
        } else {
            notifyObservers("Movie already exists!", Alert.AlertType.WARNING);
            throw new DatabaseException("Movie already exists in the watchlist.");
        }
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws DatabaseException {
        QueryBuilder<WatchlistEntity, Long> queryBuilder = dao.queryBuilder();
        List<WatchlistEntity> existingMovies;
        try {
            queryBuilder.where().eq("apiId", movie.getApiId());
            existingMovies = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query from DB on: removeFromWatchlist", e);
        }

        if (!existingMovies.isEmpty()) {
            try {
                dao.delete(existingMovies);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete DB entries!", e);
            }
        } else {
            throw new DatabaseException("Movie does not exist in the watchlist.");
        }
    }

    public List<WatchlistEntity> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query watchlist members!", e);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message, Alert.AlertType type) {
        for (Observer observer : observers) {
            observer.update(message, type);
        }
    }
        public static WatchlistRepository getInstance(){
            if (instance == null) {
                instance = new WatchlistRepository();
            }
            return instance;
        }
}

