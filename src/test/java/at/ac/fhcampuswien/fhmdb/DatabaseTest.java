package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;


class DatabaseTest {

    private Dao<WatchlistEntity, Long> watchlistDao;
    private ConnectionSource connectionSource;

    @BeforeEach
    public void setup(){
        Database database = Database.getInstance();
        connectionSource = database.getConnectionSource();
        watchlistDao = database.getWatchlistDao();
    }

    @Test
    void testWatchlistDaoNotNull() {
        Assertions.assertNotNull(watchlistDao, "WatchlistDao should not be null");
    }

    @Test
    void testConnectionSourceCreation() {
        Database database = Database.getInstance();
        Assertions.assertNotNull(database.getConnectionSource(), "ConnectionSource should not be null");
    }

    @Test
    void testTableCreation() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
            boolean tableExists = watchlistDao.isTableExists();
            Assertions.assertTrue(tableExists, "WatchlistEntity table should exist");
        } catch (SQLException e) {
            Assertions.fail("Exception occurred while creating tables: " + e.getMessage());
        }
    }

}
