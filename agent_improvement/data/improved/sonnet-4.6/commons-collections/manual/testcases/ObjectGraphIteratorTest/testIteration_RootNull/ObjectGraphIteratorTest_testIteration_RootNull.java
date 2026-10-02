package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that an ObjectGraphIterator constructed with a null root and null transformer
 * behaves as an empty iterator: hasNext() returns false, and both next() and remove()
 * throw the expected exceptions.
 */
public class ObjectGraphIteratorTest_testIteration_RootNull {

    @Test
    void testIteration_RootNull() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null, null);

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
        assertThrows(IllegalStateException.class, () -> it.remove());
    }
}
