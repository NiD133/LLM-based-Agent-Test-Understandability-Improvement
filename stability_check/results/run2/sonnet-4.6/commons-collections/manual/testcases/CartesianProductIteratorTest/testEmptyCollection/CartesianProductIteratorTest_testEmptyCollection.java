package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testEmptyCollection {

    /**
     * When one of the input iterables is empty, the Cartesian product is empty:
     * hasNext() must return false and next() must throw NoSuchElementException.
     */
    @Test
    void testEmptyCollection() {
        List<Character> letters = Arrays.asList('A', 'B', 'C');
        // Pairing letters with an empty collection yields no tuples
        CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, Collections.emptyList());

        assertFalse(it.hasNext(), "Iterator over empty collection should have no elements");
        assertThrows(NoSuchElementException.class, it::next,
                "Calling next() on an empty iterator should throw NoSuchElementException");
    }
}
