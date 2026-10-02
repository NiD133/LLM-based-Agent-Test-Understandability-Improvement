package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link BoundedIterator} behaves when it decorates an empty iterator.
 */
public class BoundedIteratorTest_testEmptyIterator {

    /**
     * Creates a BoundedIterator over an empty collection.
     * The offset and max are irrelevant because there are no elements to traverse.
     */
    private Iterator<String> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<String>emptyList().iterator(), 0, 10);
    }

    @Test
    void testEmptyIterator() {
        final Iterator<String> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for an empty iterator");

        assertThrows(NoSuchElementException.class, it::next,
                "next() should throw NoSuchElementException when the iterator is exhausted");

        assertNotNull(it.toString(), "toString() should never return null");
    }
}
