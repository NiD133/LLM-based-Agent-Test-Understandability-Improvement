package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructor_null_remove {

    /**
     * An ObjectGraphIterator constructed with null has no elements and no prior
     * call to next(), so calling remove() immediately must throw
     * IllegalStateException per the Iterator contract.
     */
    @Test
    void testIteratorConstructor_null_remove() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(IllegalStateException.class, () -> it.remove());
    }
}
