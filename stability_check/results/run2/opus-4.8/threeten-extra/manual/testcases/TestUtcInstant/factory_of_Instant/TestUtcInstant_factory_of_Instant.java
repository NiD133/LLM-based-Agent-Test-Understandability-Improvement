package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#of(Instant)}, the factory that converts a
 * {@code java.time.Instant} into a {@code UtcInstant}.
 */
public class TestUtcInstant_factory_of_Instant {

    /**
     * The Modified Julian Day for the Unix epoch, 1970-01-01.
     */
    private static final long MJD_1970_01_01 = 40587;

    @Test
    public void factory_of_Instant_convertsEpochInstantToUtcInstant() {
        // 2 nanoseconds after 1970-01-01T00:00:00Z
        Instant twoNanosAfterEpoch = Instant.ofEpochSecond(0, 2);

        UtcInstant converted = UtcInstant.of(twoNanosAfterEpoch);

        assertEquals(MJD_1970_01_01, converted.getModifiedJulianDay());
        assertEquals(2, converted.getNanoOfDay());
    }
}
