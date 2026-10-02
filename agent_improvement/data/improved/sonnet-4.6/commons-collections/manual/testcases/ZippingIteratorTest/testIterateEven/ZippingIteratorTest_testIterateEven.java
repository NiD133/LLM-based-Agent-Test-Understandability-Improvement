package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEven {

    /** Even integers: 0, 2, 4, ..., 18. */
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
     * A ZippingIterator backed by a single iterator must yield every element
     * of that iterator in order, then report hasNext() == false.
     */
    @Test
    void testIterateEven() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        for (final Integer even : evens) {
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
