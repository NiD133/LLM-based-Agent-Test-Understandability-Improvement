package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testEmptyIterator {

    private Iterator<List<Character>> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    private boolean supportsEmptyIterator() {
        return true;
    }

    private void verify() {
        // Hook retained from the original iterator test contract.
    }

    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<List<Character>> iterator = makeEmptyIterator();

        assertFalse(iterator.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(
                NoSuchElementException.class,
                iterator::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        verify();
        assertNotNull(iterator.toString());
    }
}
