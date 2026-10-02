package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEvenEven {

    private ArrayList<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int value = 0; value < 20; value += 2) {
            evens.add(value);
        }
    }

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
