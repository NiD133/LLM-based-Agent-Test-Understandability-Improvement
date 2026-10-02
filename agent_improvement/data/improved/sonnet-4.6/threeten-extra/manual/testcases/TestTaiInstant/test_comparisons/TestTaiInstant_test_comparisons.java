package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_comparisons {

    /**
     * Verifies the full ordering contract for a sorted sequence of TaiInstants:
     * compareTo, equals, isBefore, and isAfter must all agree with the element's position.
     */
    void doTest_comparisons_TaiInstant(TaiInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            TaiInstant earlier = instants[i];
            for (int j = 0; j < instants.length; j++) {
                TaiInstant later = instants[j];
                if (i < j) {
                    assertTrue(earlier.compareTo(later) < 0);
                    assertFalse(earlier.equals(later));
                    assertTrue(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                } else if (i > j) {
                    assertTrue(earlier.compareTo(later) > 0);
                    assertFalse(earlier.equals(later));
                    assertFalse(earlier.isBefore(later));
                    assertTrue(earlier.isAfter(later));
                } else {
                    assertEquals(0, earlier.compareTo(later));
                    assertTrue(earlier.equals(later));
                    assertFalse(earlier.isBefore(later));
                    assertFalse(earlier.isAfter(later));
                }
            }
        }
    }

    @Test
    public void test_comparisons() {
        doTest_comparisons_TaiInstant(
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
