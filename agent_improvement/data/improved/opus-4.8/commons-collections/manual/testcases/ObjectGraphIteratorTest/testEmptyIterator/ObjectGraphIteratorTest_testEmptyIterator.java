package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the behaviour of {@link ObjectGraphIterator} when it is created over an
 * empty source iterator.
 */
public class ObjectGraphIteratorTest_testEmptyIterator {

    /**
     * Creates an {@link ObjectGraphIterator} backed by an empty collection,
     * so the resulting graph iterator has nothing to traverse.
     *
     * @return an empty graph iterator
     */
    private ObjectGraphIterator<Object> makeEmptyIterator() {
        final List<Object> emptySource = new ArrayList<>();
        return new ObjectGraphIterator<>(emptySource.iterator());
    }

    /**
     * An empty iterator must report no elements, fail on {@code next()}, and
     * still produce a non-null {@code toString()}.
     */
    @Test
    void testEmptyIterator() {
        final Iterator<Object> it = makeEmptyIterator();

        assertFalse(it.hasNext(),
            "hasNext() should return false for an empty iterator");
        assertThrows(NoSuchElementException.class, it::next,
            "next() must throw NoSuchElementException when the iterator is exhausted");
        assertNotNull(it.toString(),
            "toString() should never return null");
    }
}
