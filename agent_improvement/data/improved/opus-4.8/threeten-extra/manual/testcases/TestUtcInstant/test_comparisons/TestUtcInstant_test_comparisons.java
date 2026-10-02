package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the natural ordering of {@link UtcInstant}, exercised through
 * {@link UtcInstant#compareTo}, {@link UtcInstant#isBefore},
 * {@link UtcInstant#isAfter} and {@link UtcInstant#equals}.
 */
public class TestUtcInstant_test_comparisons {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Asserts that {@code instants} is given in strictly ascending order by
     * checking every ordered pair: each instant must compare less-than those
     * after it, greater-than those before it, and equal to itself.
     */
    private void assertStrictlyAscending(UtcInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            UtcInstant earlier = instants[i];
            for (int j = 0; j < instants.length; j++) {
                UtcInstant later = instants[j];
                if (i < j) {
                    // earlier sits before later in the array
                    assertEquals(-1, earlier.compareTo(later));
                    assertNotEquals(earlier, later);
                    assertTrue(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                } else if (i > j) {
                    // earlier sits after later in the array
                    assertEquals(1, earlier.compareTo(later));
                    assertNotEquals(earlier, later);
                    assertFalse(earlier.isBefore(later));
                    assertTrue(earlier.isAfter(later));
                } else {
                    // same element compared with itself
                    assertEquals(0, earlier.compareTo(later));
                    assertEquals(earlier, later);
                    assertFalse(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                }
            }
        }
    }

    @Test
    public void test_comparisons() {
        // Instants listed in increasing chronological order, spanning several
        // Modified Julian Days and varying nano-of-day values within each day.
        assertStrictlyAscending(
                UtcInstant.ofModifiedJulianDay(-2L, 0),
                UtcInstant.ofModifiedJulianDay(-2L, NANOS_PER_DAY - 2),
                UtcInstant.ofModifiedJulianDay(-2L, NANOS_PER_DAY - 1),
                UtcInstant.ofModifiedJulianDay(-1L, 0),
                UtcInstant.ofModifiedJulianDay(-1L, 1),
                UtcInstant.ofModifiedJulianDay(-1L, NANOS_PER_DAY - 2),
                UtcInstant.ofModifiedJulianDay(-1L, NANOS_PER_DAY - 1),
                UtcInstant.ofModifiedJulianDay(0L, 0),
                UtcInstant.ofModifiedJulianDay(0L, 1),
                UtcInstant.ofModifiedJulianDay(0L, 2),
                UtcInstant.ofModifiedJulianDay(0L, NANOS_PER_DAY - 1),
                UtcInstant.ofModifiedJulianDay(1L, 0),
                UtcInstant.ofModifiedJulianDay(2L, 0));
    }
}
