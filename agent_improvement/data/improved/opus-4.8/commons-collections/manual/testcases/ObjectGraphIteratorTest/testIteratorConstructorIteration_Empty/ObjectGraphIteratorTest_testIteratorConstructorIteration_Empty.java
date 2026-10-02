package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies how an {@link ObjectGraphIterator} behaves when it is constructed
 * from an empty iterator-of-iterators, i.e. when there is nothing to iterate.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Empty {

    @Test
    void testIteratorConstructorIteration_Empty() {
        // An empty root iterator means the graph iterator has no elements to yield.
        final List<Iterator<Object>> emptyRoot = new ArrayList<>();
        final Iterator<Object> iterator = new ObjectGraphIterator<>(emptyRoot.iterator());

        // No elements are available.
        assertFalse(iterator.hasNext());

        // Calling next() on an exhausted iterator fails.
        assertThrows(NoSuchElementException.class, () -> iterator.next());

        // remove() before any successful next() is illegal.
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
