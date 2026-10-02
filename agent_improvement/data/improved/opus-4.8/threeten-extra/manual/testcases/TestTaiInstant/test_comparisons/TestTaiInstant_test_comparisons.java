package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the ordering operations of {@link TaiInstant}:
 * {@link TaiInstant#compareTo}, {@link TaiInstant#isBefore},
 * {@link TaiInstant#isAfter} and {@link TaiInstant#equals}.
 */
public class TestTaiInstant_test_comparisons {

    /**
     * Verifies that the supplied instants form a strictly increasing sequence
     * on the time-line by checking every ordered pair.
     * <p>
     * For each pair {@code (a, b)} taken from {@code orderedInstants}:
     * <ul>
     *   <li>if {@code a} comes earlier in the array, it must compare/sort before {@code b};</li>
     *   <li>if {@code a} comes later in the array, it must compare/sort after {@code b};</li>
     *   <li>if {@code a} and {@code b} are the same element, they must be equal.</li>
     * </ul>
     *
     * @param orderedInstants instants supplied in ascending time-line order
     */
    void assertStrictlyAscending(TaiInstant... orderedInstants) {
        for (int earlier = 0; earlier < orderedInstants.length; earlier++) {
            TaiInstant a = orderedInstants[earlier];
            for (int later = 0; later < orderedInstants.length; later++) {
                TaiInstant b = orderedInstants[later];
                if (earlier < later) {
                    // a precedes b on the time-line
                    assertTrue(a.compareTo(b) < 0);
                    assertFalse(a.equals(b));
                    assertTrue(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                } else if (earlier > later) {
                    // a follows b on the time-line
                    assertTrue(a.compareTo(b) > 0);
                    assertFalse(a.equals(b));
                    assertFalse(a.isBefore(b));
                    assertTrue(a.isAfter(b));
                } else {
                    // a and b are the same instant
                    assertEquals(0, a.compareTo(b));
                    assertTrue(a.equals(b));
                    assertFalse(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                }
            }
        }
    }

    /**
     * The comparison methods must order instants first by seconds and then by
     * nanoseconds. The instants below are listed in ascending order so that the
     * pairwise checks cover before/after/equal for every combination.
     */
    @Test
    public void test_comparisons() {
        assertStrictlyAscending(
                TaiInstant.ofTaiSeconds(-2L, 0),
                TaiInstant.ofTaiSeconds(-2L, 999999998),
                TaiInstant.ofTaiSeconds(-2L, 999999999),
                TaiInstant.ofTaiSeconds(-1L, 0),
                TaiInstant.ofTaiSeconds(-1L, 1),
                TaiInstant.ofTaiSeconds(-1L, 999999998),
                TaiInstant.ofTaiSeconds(-1L, 999999999),
                TaiInstant.ofTaiSeconds(0L, 0),
                TaiInstant.ofTaiSeconds(0L, 1),
                TaiInstant.ofTaiSeconds(0L, 2),
                TaiInstant.ofTaiSeconds(0L, 999999999),
                TaiInstant.ofTaiSeconds(1L, 0),
                TaiInstant.ofTaiSeconds(2L, 0));
    }
}
