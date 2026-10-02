package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_comparisons {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Verifies that the given instants are in strictly ascending order.
     *
     * For each pair (a, b) where a comes before b in the array:
     *   - compareTo returns a negative value
     *   - isBefore / isAfter reflect the ordering
     *   - equals returns false
     *
     * Equal positions (same index) must compare as 0 and be equal.
     */
    void assertInstantsInStrictAscendingOrder(UtcInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            UtcInstant a = instants[i];
            for (int j = 0; j < instants.length; j++) {
                UtcInstant b = instants[j];
                if (i < j) {
                    assertEquals(-1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertTrue(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                } else if (i > j) {
                    assertEquals(1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertTrue(a.isAfter(b));
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                }
            }
        }
    }

    @Test
    public void test_comparisons() {
        assertInstantsInStrictAscendingOrder(
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
                UtcInstant.ofModifiedJulianDay(2L, 0)
        );
    }
}
