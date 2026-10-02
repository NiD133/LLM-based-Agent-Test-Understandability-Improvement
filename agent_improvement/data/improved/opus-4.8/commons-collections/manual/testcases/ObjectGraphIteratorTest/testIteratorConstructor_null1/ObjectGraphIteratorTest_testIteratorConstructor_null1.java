package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies how an {@link ObjectGraphIterator} behaves when it is constructed
 * from a {@code null} root iterator. Such an iterator should act as an empty
 * iterator that yields no elements.
 */
public class ObjectGraphIteratorTest_testIteratorConstructor_null1 {

    @Test
    void testIteratorConstructor_null1() {
        final Iterator<Object> emptyIterator = new ObjectGraphIterator<>(null);

        // A null root means there is nothing to iterate over.
        assertFalse(emptyIterator.hasNext());

        // Asking for an element from an empty iterator fails.
        assertThrows(NoSuchElementException.class, () -> emptyIterator.next());

        // remove() is illegal because next() has never returned an element.
        assertThrows(IllegalStateException.class, () -> emptyIterator.remove());
    }
}
