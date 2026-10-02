package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testEmptyIterator {

    /**
     * Creates a BoundedIterator that wraps an empty collection.
     * Offset 0, max 10 — but since the source is empty, no elements are ever returned.
     */
    private Iterator<String> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<String>emptyList().iterator(), 0, 10);
    }

    /**
     * Verifies that a BoundedIterator backed by an empty source collection
     * correctly reports no elements and throws on any attempt to advance.
     */
    @Test
    void testEmptyIterator() {
        final Iterator<String> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
