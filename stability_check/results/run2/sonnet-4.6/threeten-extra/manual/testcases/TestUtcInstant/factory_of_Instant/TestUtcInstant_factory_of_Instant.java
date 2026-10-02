package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    // MJD 40587 corresponds to 1970-01-01, the Unix epoch date
    private static final long MJD_UNIX_EPOCH = 40587;

    @Test
    public void factory_of_Instant() {
        // Create a UTC instant from a Java Instant at epoch second 0, nano 2
        // (i.e. 1970-01-01T00:00:00.000000002Z in UTC-SLS)
        Instant input = Instant.ofEpochSecond(0, 2);
        UtcInstant test = UtcInstant.of(input);

        // The resulting UTC instant should fall on the Unix epoch day (MJD 40587)
        assertEquals(MJD_UNIX_EPOCH, test.getModifiedJulianDay());

        // The 2-nanosecond offset from epoch second 0 is preserved as nano-of-day
        assertEquals(2, test.getNanoOfDay());
    }
}
