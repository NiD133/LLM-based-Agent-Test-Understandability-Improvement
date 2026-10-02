package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay} correctly constructs an instant
 * that falls within the leap second inserted at the end of 1972-12-31 (the very
 * first UTC leap second).
 *
 * <p>Background: a leap-second day has 86,401 SI seconds rather than the usual
 * 86,400, so valid nanosecond-of-day values range from 0 to
 * {@code 86_401_000_000_000} inclusive.  The value {@code 86_400_000_000_000}
 * (i.e. exactly one ordinary day's worth of nanoseconds) falls inside that extra
 * second, meaning the clock reads 23:59:60 — the leap second itself.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_startLeap {

    // MJD 41682 is 1972-12-31, which carries the first positive leap second.
    private static final long MJD_1972_12_31_LEAP = 41682;

    // Exactly 86,400 seconds expressed in nanoseconds — the nanosecond offset that
    // marks the very start of the leap second (23:59:60.000000000 UTC).
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        // Construct an instant at the very start of the 1972-12-31 leap second.
        UtcInstant t = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY);

        // The MJD must be preserved unchanged.
        assertEquals(MJD_1972_12_31_LEAP, t.getModifiedJulianDay());

        // The nano-of-day must be preserved unchanged (= start of leap second).
        assertEquals(NANOS_PER_DAY, t.getNanoOfDay());

        // nanoOfDay >= 86_400s worth of nanos ⟹ the instant is within the leap second.
        assertTrue(t.isLeapSecond());

        // The canonical string representation of 23:59:60 on 1972-12-31.
        assertEquals("1972-12-31T23:59:60Z", t.toString());
    }
}
