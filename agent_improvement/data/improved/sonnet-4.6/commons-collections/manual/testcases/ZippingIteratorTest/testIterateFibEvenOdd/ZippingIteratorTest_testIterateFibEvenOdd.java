package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateFibEvenOdd {

    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> fib;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                evens.add(i);  // 0, 2, 4, ..., 18
            } else {
                odds.add(i);   // 1, 3, 5, ..., 19
            }
        }
        fib = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21));
    }

    @Test
    void testIterateFibEvenOdd() {
        // ZippingIterator interleaves three iterators round-robin: fib, evens, odds.
        // Once fib (8 elements) is exhausted, it continues with the remaining evens and odds.
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(fib.iterator(), evens.iterator(), odds.iterator());

        // Expected interleaved sequence: rounds of (fib, even, odd) while fib lasts,
        // then pairs of (even, odd), then nothing.
        final List<Integer> expected = Arrays.asList(
            // fib,  even,  odd
               1,    0,     1,   // round 1
               1,    2,     3,   // round 2
               2,    4,     5,   // round 3
               3,    6,     7,   // round 4
               5,    8,     9,   // round 5
               8,    10,    11,  // round 6
               13,   12,    13,  // round 7
               21,   14,    15,  // round 8 — fib exhausted after this
            // even,  odd (fib gone)
               16,   17,        // round 9
               18,   19         // round 10
        );

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), iter.next(),
                "Mismatch at position " + i);
        }
        assertFalse(iter.hasNext(), "Iterator should be exhausted");
    }
}
