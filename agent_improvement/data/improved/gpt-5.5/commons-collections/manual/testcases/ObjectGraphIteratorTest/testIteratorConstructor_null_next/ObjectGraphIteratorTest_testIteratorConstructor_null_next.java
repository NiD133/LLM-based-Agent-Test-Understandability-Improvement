package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructor_null_next {

    @Test
    void testIteratorConstructor_null_next() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(null);

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
