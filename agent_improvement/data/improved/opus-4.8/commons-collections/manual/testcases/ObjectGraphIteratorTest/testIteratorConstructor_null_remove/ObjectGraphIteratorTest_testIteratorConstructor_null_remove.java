package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObjectGraphIterator#remove()} when the iterator was built from a
 * {@code null} root iterator.
 */
public class ObjectGraphIteratorTest_testIteratorConstructor_null_remove {

    /**
     * When an ObjectGraphIterator is created with a {@code null} root iterator,
     * nothing has been returned yet, so calling {@code remove()} must fail with
     * an {@link IllegalStateException}.
     */
    @Test
    void testIteratorConstructor_null_remove() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(null);

        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
