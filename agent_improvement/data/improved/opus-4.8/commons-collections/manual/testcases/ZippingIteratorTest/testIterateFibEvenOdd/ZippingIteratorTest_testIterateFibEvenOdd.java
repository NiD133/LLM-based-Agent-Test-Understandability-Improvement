package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ZippingIterator} interleaves three child iterators in a
 * strict round-robin order, and that a child iterator which runs out of
 * elements is simply skipped while the remaining children keep being visited.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateFibEvenOdd {

    /** The first eight Fibonacci numbers (the shortest child, length 8). */
    private List<Integer> fib;

    /** Even numbers in [0, 20): 0, 2, 4, ... 18 (length 10). */
    private List<Integer> evens;

    /** Odd numbers in [0, 20): 1, 3, 5, ... 19 (length 10). */
    private List<Integer> odds;

    @BeforeEach
    public void setUp() {
        fib = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21);

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
    void testIterateFibEvenOdd() {
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(fib.iterator(), evens.iterator(), odds.iterator());

        // The children are visited in the order [fib, even, odd] each round.
        // Once "fib" is exhausted (after 8 rounds) only "even" and "odd" remain,
        // so the tail of the sequence is just the leftover even/odd pairs.
        final List<Integer> expectedOrder = Arrays.asList(
                // fib, even, odd
                1,  0,  1,
                1,  2,  3,
                2,  4,  5,
                3,  6,  7,
                5,  8,  9,
                8, 10, 11,
               13, 12, 13,
               21, 14, 15,
                   // fib exhausted from here on, only even/odd remain
                   16, 17,
                   18, 19);

        for (final Integer expected : expectedOrder) {
            assertEquals(expected, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
