package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BoundedIterator#remove()} deletes the current element
 * from the underlying collection while the iterator keeps yielding the rest of
 * its bounded range.
 */
public class BoundedIteratorTest_testRemoveFirst {

    /** Source elements decorated by the bounded iterator under test. */
    private static final List<String> SOURCE_ELEMENTS =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * With offset 1 and max 5, the bounded iterator skips "a" and exposes
     * exactly "b", "c", "d", "e", "f". Removing the first exposed element
     * ("b") must delete it from the backing list, after which the remaining
     * elements are still returned in order and the range then ends.
     */
    @Test
    void testRemoveFirst() {
        final List<String> backingList = new ArrayList<>(SOURCE_ELEMENTS);
        final Iterator<String> boundedIterator =
                new BoundedIterator<>(backingList.iterator(), 1, 5);

        // First exposed element is "b" (offset 1 skips "a").
        assertTrue(boundedIterator.hasNext());
        assertEquals("b", boundedIterator.next());

        // Removing "b" must delete it from the backing collection.
        boundedIterator.remove();
        assertFalse(backingList.contains("b"));

        // The remaining elements within the range are still returned in order.
        assertTrue(boundedIterator.hasNext());
        assertEquals("c", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("d", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("e", boundedIterator.next());
        assertTrue(boundedIterator.hasNext());
        assertEquals("f", boundedIterator.next());

        // The bounded range is exhausted; further access throws.
        assertFalse(boundedIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> boundedIterator.next());
    }
}
