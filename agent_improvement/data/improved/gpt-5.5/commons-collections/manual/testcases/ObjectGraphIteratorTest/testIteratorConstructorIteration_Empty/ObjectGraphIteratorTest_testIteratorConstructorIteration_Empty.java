package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Empty {

    @Test
    void testIteratorConstructorIteration_Empty() {
        final List<Iterator<Object>> emptyRootIterators = new ArrayList<>();
        final Iterator<Object> iterator = new ObjectGraphIterator<>(emptyRootIterators.iterator());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
