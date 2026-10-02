package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link CartesianProductIterator} built with no input iterables
 * behaves as a properly exhausted iterator.
 */
public class CartesianProductIteratorTest_testEmptyIterator {

    /**
     * Creates a Cartesian product iterator with no input iterables, which is
     * therefore empty.
     */
    private CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    @Test
    void testEmptyIterator() {
        final Iterator<List<Character>> it = makeEmptyIterator();

        // An empty iterator has no elements to traverse.
        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");

        // Asking for the next element must fail because the iterator is exhausted.
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");

        // toString() must still produce a non-null representation.
        assertNotNull(it.toString());
    }
}
