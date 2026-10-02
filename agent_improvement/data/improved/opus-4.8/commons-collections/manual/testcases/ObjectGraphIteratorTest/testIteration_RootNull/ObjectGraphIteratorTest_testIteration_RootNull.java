package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link ObjectGraphIterator} behaves when constructed from a
 * {@code null} root object and a {@code null} transformer.
 *
 * <p>With no root and no transformer there is nothing to iterate over, so the
 * iterator must behave as a permanently empty iterator.</p>
 */
public class ObjectGraphIteratorTest_testIteration_RootNull {

    @Test
    void testIteration_RootNull() {
        // A null root with a null transformer yields an empty iterator.
        final Iterator<Object> emptyIterator = new ObjectGraphIterator<>(null, null);

        // There is no element to return.
        assertFalse(emptyIterator.hasNext());

        // Asking for the next element fails because the iterator is empty.
        assertThrows(NoSuchElementException.class, () -> emptyIterator.next());

        // remove() is illegal because next() was never successfully called.
        assertThrows(IllegalStateException.class, () -> emptyIterator.remove());
    }
}
