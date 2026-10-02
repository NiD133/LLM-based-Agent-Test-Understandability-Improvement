package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateOddEven {

    // 10 even numbers: [0, 2, 4, 6, 8, 10, 12, 14, 16, 18]
    private ArrayList<Integer> evens;

    // 10 odd numbers: [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
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
     * Verifies that ZippingIterator strictly interleaves two iterators: the
     * first iterator contributes every other element starting at position 0,
     * and the second iterator contributes every other element starting at
     * position 1.
     *
     * Expected sequence: odds[0], evens[0], odds[1], evens[1], ..., odds[9], evens[9]
     */
    @Test
    void testIterateOddEven() {
        // odds is supplied first, so it produces elements at even positions (0, 2, 4, ...)
        // evens is supplied second, so it produces elements at odd positions (1, 3, 5, ...)
        final ZippingIterator<Integer> iter = new ZippingIterator<>(odds.iterator(), evens.iterator());

        // Both lists have 10 elements; one pair is consumed per loop iteration
        for (int i = 0; i < 10; i++) {
            assertTrue(iter.hasNext());
            assertEquals(odds.get(i).intValue(), iter.next());  // element from 'odds' iterator

            assertTrue(iter.hasNext());
            assertEquals(evens.get(i).intValue(), iter.next()); // element from 'evens' iterator
        }

        // All 20 elements (10 odds + 10 evens) have been consumed
        assertFalse(iter.hasNext());
    }
}
