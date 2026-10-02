package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a CartesianProductIterator over any collection set containing
 * an empty collection produces an empty iteration (no elements, immediate exception on next()).
 */
public class CartesianProductIteratorTest_testEmptyCollection {

    // A non-empty list paired with an empty list should yield zero Cartesian product tuples
    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    /**
     * When one of the input collections is empty, the Cartesian product is empty:
     * hasNext() must return false, and next() must throw NoSuchElementException.
     */
    @Test
    void testEmptyCollection() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, Collections.emptyList());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }
}
