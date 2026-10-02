package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ZippingIterator#remove()} when zipping two child iterators:
 * removing an element through the zipping iterator must remove it from the
 * underlying source list as well.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromDouble {

    /** Even numbers in [0, 20): 0, 2, 4, ... 18. */
    private ArrayList<Integer> evens;

    /** Odd numbers in [0, 20): 1, 3, 5, ... 19. */
    private ArrayList<Integer> odds;

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

    @Test
    void testRemoveFromDouble() {
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(evens.iterator(), odds.iterator());

        // Start from the total element count and decrement for every element
        // we remove, so the expected size mirrors the removals exactly.
        int expectedSize = evens.size() + odds.size();
        while (iter.hasNext()) {
            final Integer value = iter.next();
            final boolean removable = value % 4 == 0 || value % 3 == 0;
            if (removable) {
                iter.remove();
                expectedSize--;
            }
        }

        // remove() must propagate to the backing lists.
        assertEquals(expectedSize, evens.size() + odds.size());
    }
}
