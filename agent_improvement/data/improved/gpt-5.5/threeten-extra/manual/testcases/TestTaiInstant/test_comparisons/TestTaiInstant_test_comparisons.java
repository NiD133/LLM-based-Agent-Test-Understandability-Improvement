package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_comparisons {

    @Test
    public void test_comparisons() {
        assertOrderedComparisonBehavior(
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

    private void assertOrderedComparisonBehavior(TaiInstant... orderedInstants) {
        for (int leftIndex = 0; leftIndex < orderedInstants.length; leftIndex++) {
            TaiInstant left = orderedInstants[leftIndex];
            for (int rightIndex = 0; rightIndex < orderedInstants.length; rightIndex++) {
                TaiInstant right = orderedInstants[rightIndex];
                assertComparisonMatchesOrder(leftIndex, left, rightIndex, right);
            }
        }
    }

    private void assertComparisonMatchesOrder(
            int leftIndex,
            TaiInstant left,
            int rightIndex,
            TaiInstant right) {

        if (leftIndex < rightIndex) {
            assertLeftIsBeforeRight(left, right);
        } else if (leftIndex > rightIndex) {
            assertLeftIsAfterRight(left, right);
        } else {
            assertInstantsAreEqual(left, right);
        }
    }

    private void assertLeftIsBeforeRight(TaiInstant left, TaiInstant right) {
        assertTrue(left.compareTo(right) < 0);
        assertFalse(left.equals(right));
        assertTrue(left.isBefore(right));
        assertFalse(left.isAfter(right));
    }

    private void assertLeftIsAfterRight(TaiInstant left, TaiInstant right) {
        assertTrue(left.compareTo(right) > 0);
        assertFalse(left.equals(right));
        assertFalse(left.isBefore(right));
        assertTrue(left.isAfter(right));
    }

    private void assertInstantsAreEqual(TaiInstant left, TaiInstant right) {
        assertEquals(0, left.compareTo(right));
        assertTrue(left.equals(right));
        assertFalse(left.isBefore(right));
        assertFalse(left.isAfter(right));
    }
}
