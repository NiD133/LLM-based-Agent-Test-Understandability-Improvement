package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructor_null_remove {

    @Test
    void testIteratorConstructor_null_remove() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(null);

        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
