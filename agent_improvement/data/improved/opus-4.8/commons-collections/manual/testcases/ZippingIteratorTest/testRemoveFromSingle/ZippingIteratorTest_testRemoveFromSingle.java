package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ZippingIterator#remove()} deletes elements from the
 * single backing iterator it is wrapping.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromSingle {

    /** The even numbers 0, 2, 4, ... 18, used as the only backing list. */
    private ArrayList<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int i = 0; i < 20; i += 2) {
            evens.add(i);
        }
    }

    @Test
    void testRemoveFromSingle() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());

        // Remove every element divisible by 4 while iterating, tracking how
        // many should remain in the backing list afterwards.
        int expectedSize = evens.size();
        while (iter.hasNext()) {
            final Integer value = iter.next();
            if (value % 4 == 0) {
                expectedSize--;
                iter.remove();
            }
        }

        assertEquals(expectedSize, evens.size());
    }
}
