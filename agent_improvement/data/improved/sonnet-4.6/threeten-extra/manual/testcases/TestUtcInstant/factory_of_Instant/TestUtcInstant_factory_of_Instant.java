package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    // 1970-01-01 (the Unix epoch) expressed as a Modified Julian Day (days since 1858-11-17)
    private static final long MJD_UNIX_EPOCH = 40587;

    @Test
    public void factory_of_Instant() {
        // 1970-01-01T00:00:00.000000002Z: epoch second 0, with 2 nanoseconds offset
        Instant input = Instant.ofEpochSecond(0, 2);

        UtcInstant utcInstant = UtcInstant.of(input);

        assertEquals(MJD_UNIX_EPOCH, utcInstant.getModifiedJulianDay());
        assertEquals(2, utcInstant.getNanoOfDay());
    }
}
