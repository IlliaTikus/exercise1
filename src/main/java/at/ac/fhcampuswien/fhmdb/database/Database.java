package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String DB_URL;
    private static String username;
    private static String password;
    private ConnectionSource connectionSource;
    private Dao<WatchlistEntity, Long> dao;

    private static Database instance;

    private Database() throws DatabaseException{
        try {
            loadConfigurations();
            createConnectionSource();
            createDao();
            createTables();
        } catch (SQLException | IOException e) {
            throw new DatabaseException("Error loading database!", e);
        }
    }

    private void loadConfigurations() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./config.properties");
        properties.load(fileInputStream);
        fileInputStream.close();

        this.DB_URL = properties.getProperty("db.url");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
    }

    private void createConnectionSource() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(this.DB_URL, this.username, this.password);
    }

    private void createDao() throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
    }

    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
    }

    public static Database getInstance() throws DatabaseException{
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Dao<WatchlistEntity, Long> getWatchlistDao() {
        return this.dao;
    }
}