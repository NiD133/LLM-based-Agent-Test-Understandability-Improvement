package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay} correctly constructs an instant
 * at the very last nanosecond of the normal (non-leap) portion of a leap-second day.
 *
 * <p>1972-12-31 (MJD 41682) was a leap-second day, so its timeline looks like:
 * <pre>
 *   23:59:58 ... 23:59:59.999999999  &lt;- normal day  (nanoOfDay = NANOS_PER_DAY - 1)
 *   23:59:60.000000000               &lt;- leap second  (nanoOfDay = NANOS_PER_DAY)
 * </pre>
 * This test verifies the boundary instant at {@code nanoOfDay = NANOS_PER_DAY - 1}:
 * it must not be considered a leap second and must format as {@code 23:59:59.999999999Z}.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endNormal {

    /** MJD for 1972-12-31, a positive leap-second day. */
    private static final long MJD_1972_12_31_LEAP = 41682L;

    /** Nanoseconds in a standard 86400-second day. */
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        // Last nanosecond before the leap second begins on 1972-12-31
        long lastNormalNano = NANOS_PER_DAY - 1;

        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, lastNormalNano);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(lastNormalNano, instant.getNanoOfDay());
        assertFalse(instant.isLeapSecond(), "instant just before leap second should not be a leap second");
        assertEquals("1972-12-31T23:59:59.999999999Z", instant.toString());
    }
}
