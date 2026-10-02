package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ZippingIterator} interleaves two child iterators,
 * alternating one element from each.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEvenOdd {

    /** Holds the even numbers 0, 2, 4, ... 18. */
    private List<Integer> evens;

    /** Holds the odd numbers 1, 3, 5, ... 19. */
    private List<Integer> odds;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                evens.add(i);
            } else {
                odds.add(i);
            }
        }
    }

    /**
     * Zipping the evens iterator with the odds iterator should reproduce the
     * natural sequence 0, 1, 2, 3, ... 19, then report exhaustion.
     */
    @Test
    void testIterateEvenOdd() {
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(evens.iterator(), odds.iterator());

        for (int expected = 0; expected < 20; expected++) {
            assertTrue(iter.hasNext());
            assertEquals(Integer.valueOf(expected), iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
