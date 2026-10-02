package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructor_null1 {

    @Test
    void testIteratorConstructor_null1() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(null);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
