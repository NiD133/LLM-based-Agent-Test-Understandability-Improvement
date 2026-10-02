package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    private static final long UNIX_EPOCH_MODIFIED_JULIAN_DAY = 40587L;
    private static final int TWO_NANOSECONDS = 2;

    @Test
    public void factory_of_Instant() {
        Instant epochPlusTwoNanos = Instant.ofEpochSecond(0, 2);

        UtcInstant test = UtcInstant.of(epochPlusTwoNanos);

        assertEquals(UNIX_EPOCH_MODIFIED_JULIAN_DAY, test.getModifiedJulianDay());
        assertEquals(TWO_NANOSECONDS, test.getNanoOfDay());
    }
}
