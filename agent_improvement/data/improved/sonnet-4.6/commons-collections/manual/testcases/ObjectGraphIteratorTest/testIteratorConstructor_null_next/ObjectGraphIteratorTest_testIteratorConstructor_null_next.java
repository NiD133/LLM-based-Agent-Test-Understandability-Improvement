package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructor_null_next {

    /**
     * When constructed with a null root iterator, calling next() on the
     * ObjectGraphIterator must throw NoSuchElementException because there
     * are no elements to iterate over.
     */
    @Test
    void testIteratorConstructor_null_next() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
