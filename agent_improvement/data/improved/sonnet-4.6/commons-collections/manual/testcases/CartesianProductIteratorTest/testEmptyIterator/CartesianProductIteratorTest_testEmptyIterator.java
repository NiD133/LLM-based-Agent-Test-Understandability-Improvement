package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link CartesianProductIterator} constructed with no input iterables
 * behaves as an empty iterator: {@code hasNext()} returns false and {@code next()}
 * throws {@link NoSuchElementException}.
 */
public class CartesianProductIteratorTest_testEmptyIterator {

    /** Creates an iterator over the empty Cartesian product (no input iterables). */
    private CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    /** No-op hook for subclass cross-verification; nothing to verify here. */
    public void verify() {
        // do nothing
    }

    /** @return true, because this test exercises the empty-iterator path */
    public boolean supportsEmptyIterator() {
        return true;
    }

    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<List<Character>> it = makeEmptyIterator();

        // An empty Cartesian product has no tuples.
        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");

        // Calling next() on an exhausted iterator must throw NoSuchElementException.
        assertThrows(
                NoSuchElementException.class,
                it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");

        verify();

        // toString() must always return a non-null string.
        assertNotNull(it.toString());
    }
}
