package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    // MJD value for 1970-01-01 (the Unix epoch), where MJD 0 = 1858-11-17
    private static final long MJD_UNIX_EPOCH = 40587;

    @Test
    public void factory_of_Instant() {
        // 1970-01-01T00:00:00.000000002Z — epoch second 0 with 2 nanoseconds offset
        UtcInstant test = UtcInstant.of(Instant.ofEpochSecond(0, 2));
        assertEquals(MJD_UNIX_EPOCH, test.getModifiedJulianDay());
        assertEquals(2, test.getNanoOfDay());
    }
}
