package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ZippingIterator.remove() correctly delegates removal to the underlying
 * single iterator, keeping the backing list consistent.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromSingle {

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
     * Wraps a single list in a ZippingIterator and removes all multiples of 4.
     * Verifies that the backing list shrinks by exactly the number of removed elements.
     */
    @Test
    void testRemoveFromSingle() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        int expectedSize = evens.size();

        while (iter.hasNext()) {
            final Integer val = iter.next();
            if (val % 4 == 0) {
                expectedSize--;
                iter.remove();
            }
        }

        assertEquals(expectedSize, evens.size());
    }
}
