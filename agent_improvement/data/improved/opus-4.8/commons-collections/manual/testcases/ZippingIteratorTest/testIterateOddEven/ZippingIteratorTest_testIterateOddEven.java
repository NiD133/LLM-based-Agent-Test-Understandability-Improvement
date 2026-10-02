package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ZippingIterator} interleaves two child iterators by
 * alternating between them on each call to {@link ZippingIterator#next()}.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateOddEven {

    /** Even numbers in [0, 20): 0, 2, 4, ..., 18. */
    private List<Integer> evens;

    /** Odd numbers in [0, 20): 1, 3, 5, ..., 19. */
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
     * Zipping {@code odds} with {@code evens} should yield their elements
     * interleaved: odds[0], evens[0], odds[1], evens[1], ... until both child
     * iterators are exhausted.
     */
    @Test
    void testIterateOddEven() {
        final ZippingIterator<Integer> zippingIterator =
                new ZippingIterator<>(odds.iterator(), evens.iterator());

        // Each pair of next() calls consumes one element from each list, so the
        // shared index into both lists only advances after the even-position read.
        int listIndex = 0;
        for (int position = 0; position < 20; position++) {
            assertTrue(zippingIterator.hasNext());
            final int actual = zippingIterator.next();
            if (position % 2 == 0) {
                assertEquals(odds.get(listIndex).intValue(), actual);
            } else {
                assertEquals(evens.get(listIndex).intValue(), actual);
                listIndex++;
            }
        }

        assertFalse(zippingIterator.hasNext());
    }
}
