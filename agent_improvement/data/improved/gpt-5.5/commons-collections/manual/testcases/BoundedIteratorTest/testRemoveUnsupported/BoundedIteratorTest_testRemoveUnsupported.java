package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveUnsupported {

    /**
     * Verifies that {@link BoundedIterator#remove()} delegates to the wrapped iterator
     * after the bounded iterator has returned an element.
     */
    @Test
    void testRemoveUnsupported() {
        final List<String> testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        final Iterator<String> iteratorWithoutRemove = new AbstractIteratorDecorator<String>(testList.iterator()) {

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        final Iterator<String> iter = new BoundedIterator<>(iteratorWithoutRemove, 1, 5);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        final UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> iter.remove());
        assertNull(thrown.getMessage());
    }
}
