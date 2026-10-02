package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedIterator} returns only the elements that fall inside
 * its bounded range {@code [offset, offset + max)} of the decorated iterator.
 */
public class BoundedIteratorTest_testBounded {

    /** Source elements, indexed 0..6, that the iterator decorates. */
    private final List<String> sourceElements =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * With an offset of 2 and a maximum of 4 elements, the iterator should skip
     * the first two elements ("a", "b") and then return exactly the next four
     * ("c", "d", "e", "f"), stopping before reaching "g".
     */
    @Test
    void testBounded() {
        final int offset = 2;
        final int max = 4;
        final Iterator<String> boundedIterator =
                new BoundedIterator<>(sourceElements.iterator(), offset, max);

        // The four elements inside the bounded range are returned in order.
        assertTrue(boundedIterator.hasNext());
        assertEquals("c", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("d", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("e", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("f", boundedIterator.next());

        // Once the bound is reached the iterator is exhausted.
        assertFalse(boundedIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> boundedIterator.next(),
                "Expected NoSuchElementException once the bounded range is exhausted.");
    }
}
