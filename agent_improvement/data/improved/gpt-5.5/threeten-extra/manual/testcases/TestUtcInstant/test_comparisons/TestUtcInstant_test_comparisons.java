package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_comparisons {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    private void assertTimelineOrder(UtcInstant... orderedInstants) {
        for (int leftIndex = 0; leftIndex < orderedInstants.length; leftIndex++) {
            UtcInstant left = orderedInstants[leftIndex];
            for (int rightIndex = 0; rightIndex < orderedInstants.length; rightIndex++) {
                UtcInstant right = orderedInstants[rightIndex];

                if (leftIndex < rightIndex) {
                    assertEquals(-1, left.compareTo(right));
                    assertNotEquals(left, right);
                    assertTrue(left.isBefore(right));
                    assertFalse(left.isAfter(right));
                } else if (leftIndex > rightIndex) {
                    assertEquals(1, left.compareTo(right));
                    assertNotEquals(left, right);
                    assertFalse(left.isBefore(right));
                    assertTrue(left.isAfter(right));
                } else {
                    assertEquals(0, left.compareTo(right));
                    assertEquals(left, right);
                    assertFalse(left.isBefore(right));
                    assertFalse(left.isAfter(right));
                }
            }
        }
    }

    @Test
    public void test_comparisons() {
        assertTimelineOrder(
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
