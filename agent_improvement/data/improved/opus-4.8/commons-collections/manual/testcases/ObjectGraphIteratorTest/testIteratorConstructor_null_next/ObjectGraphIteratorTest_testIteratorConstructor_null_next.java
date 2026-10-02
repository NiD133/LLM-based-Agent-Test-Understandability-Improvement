package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link ObjectGraphIterator} created from a {@code null} root
 * iterator behaves as an empty iterator: calling {@code next()} throws
 * {@link NoSuchElementException} because there is nothing to return.
 */
public class ObjectGraphIteratorTest_testIteratorConstructor_null_next {

    @Test
    void testIteratorConstructor_null_next() {
        // A null root iterator yields an empty ObjectGraphIterator.
        final Iterator<Object> emptyIterator = new ObjectGraphIterator<>(null);

        // Requesting an element from the empty iterator must fail.
        assertThrows(NoSuchElementException.class, () -> emptyIterator.next());
    }
}
