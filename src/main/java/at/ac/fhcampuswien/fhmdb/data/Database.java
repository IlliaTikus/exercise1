package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:h2:file: ./db/watchlist"; // TODO DB URL input
    private static final String username = "user"; // TODO DB username input
    private static final String password = "password"; // TODO DB password input
    private ConnectionSource connectionSource;
    private Dao<WatchlistMovieEntity, Long> dao;

    private static Database instance;

    private Database() {
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public void createConnectionSource() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(this.DB_URL, this.username, this.password);
    }

    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.dao;
    }
}