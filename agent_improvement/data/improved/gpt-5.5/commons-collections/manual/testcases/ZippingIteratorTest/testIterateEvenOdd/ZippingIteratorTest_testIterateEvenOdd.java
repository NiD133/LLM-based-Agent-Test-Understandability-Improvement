package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ZippingIteratorTest_testIterateEvenOdd {

    private static final int NUMBER_COUNT = 20;

    private List<Integer> evens;
    private List<Integer> odds;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();

        for (int number = 0; number < NUMBER_COUNT; number++) {
            if (number % 2 == 0) {
                evens.add(number);
            } else {
                odds.add(number);
            }
        }
    }

    @Test
    void testIterateEvenOdd() {
        final ZippingIterator<Integer> iterator = new ZippingIterator<>(evens.iterator(), odds.iterator());

        for (int expectedNumber = 0; expectedNumber < NUMBER_COUNT; expectedNumber++) {
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(expectedNumber), iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
