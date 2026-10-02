package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteration_RootNull {

    @Test
    void testIteration_RootNull() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(null, null);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
