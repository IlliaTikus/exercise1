package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class WatchlistRepositoryTest {
    private WatchlistRepository watchlistRepository;

    @BeforeEach
    public void setUp() {
        WatchlistRepository.getInstance();
    }

    @Test
    public void testGetInstance() throws DatabaseException {
        // Call getInstance for the first time
        WatchlistRepository instance1 = WatchlistRepository.getInstance();

        // Assert that the instance is not null
        assertNotNull(instance1);

        // Call getInstance again
        WatchlistRepository instance2 = WatchlistRepository.getInstance();

        // Assert that the second instance is the same as the first instance
        assertSame(instance1, instance2);
    }
}