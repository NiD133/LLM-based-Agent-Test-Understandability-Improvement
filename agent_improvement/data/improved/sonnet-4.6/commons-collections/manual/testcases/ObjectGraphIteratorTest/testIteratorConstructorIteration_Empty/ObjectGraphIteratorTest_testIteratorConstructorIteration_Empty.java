package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Empty {

    /**
     * Verifies that an ObjectGraphIterator wrapping an empty iterator-of-iterators
     * reports no elements, throws NoSuchElementException on next(), and throws
     * IllegalStateException on remove() when no element has been returned yet.
     */
    @Test
    void testIteratorConstructorIteration_Empty() {
        final List<Iterator<Object>> emptyIteratorList = new ArrayList<>();
        final Iterator<Object> iterator = new ObjectGraphIterator<>(emptyIteratorList.iterator());

        assertFalse(iterator.hasNext(), "Empty iterator should have no elements");
        assertThrows(NoSuchElementException.class, () -> iterator.next(),
                "next() on empty iterator should throw NoSuchElementException");
        assertThrows(IllegalStateException.class, () -> iterator.remove(),
                "remove() before any next() call should throw IllegalStateException");
    }
}
