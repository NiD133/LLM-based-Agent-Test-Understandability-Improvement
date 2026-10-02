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

    private List<Integer> evens;
    private List<Integer> odds;
    private List<Integer> fib;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int value = 0; value < 20; value++) {
            if (value % 2 == 0) {
                evens.add(value);
            } else {
                odds.add(value);
            }
        }

        fib = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21);
    }

    @Test
    void testIterateFibEvenOdd() {
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(fib.iterator(), evens.iterator(), odds.iterator());
        final List<Integer> expectedValues = Arrays.asList(
                1, 0, 1,
                1, 2, 3,
                2, 4, 5,
                3, 6, 7,
                5, 8, 9,
                8, 10, 11,
                13, 12, 13,
                21, 14, 15,
                16, 17,
                18, 19);

        for (final Integer expectedValue : expectedValues) {
            assertEquals(expectedValue, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
