package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ZippingIterator correctly interleaves two iterators in round-robin order.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEvenOdd {

    // Even numbers: [0, 2, 4, ..., 18]
    private ArrayList<Integer> evens;

    // Odd numbers: [1, 3, 5, ..., 19]
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

    /**
     * Zipping [0,2,4,...,18] with [1,3,5,...,19] should produce the sequential
     * integers 0 through 19 in order, since the iterator alternates between the
     * two sources one element at a time.
     */
    @Test
    void testIterateEvenOdd() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        for (int i = 0; i < 20; i++) {
            assertTrue(iter.hasNext());
            assertEquals(Integer.valueOf(i), iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
