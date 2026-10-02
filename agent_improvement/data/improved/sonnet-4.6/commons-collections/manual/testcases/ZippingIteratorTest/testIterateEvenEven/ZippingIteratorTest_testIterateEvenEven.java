package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ZippingIterator correctly interleaves elements from two identical
 * iterators, yielding each element twice in sequence before moving to the next.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEvenEven {

    /** Even numbers [0, 2, 4, ..., 18] used as input to both zipped iterators. */
    private ArrayList<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }
    }

    /**
     * Zipping two iterators over the same list must produce each element exactly
     * twice in a row: [0, 0, 2, 2, 4, 4, ...].
     */
    @Test
    void testIterateEvenEven() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), evens.iterator());
        for (final Integer even : evens) {
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
