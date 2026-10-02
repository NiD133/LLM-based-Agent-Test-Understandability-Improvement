package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that iterating forward with {@link LoopingListIterator#next()} and
 * deleting each element via {@link LoopingListIterator#remove()} empties the
 * wrapped list one element at a time, and that the iterator reports no further
 * elements once the list is empty.
 */
public class LoopingListIteratorTest_testRemovingElementsAndIteratingForward {

    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> backingList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> iterator = new LoopingListIterator<>(backingList);

        // Iterate to "a" and remove it, leaving [b, c].
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        iterator.remove();
        assertEquals(2, backingList.size());

        // Iterate to "b" and remove it, leaving [c].
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());
        iterator.remove();
        assertEquals(1, backingList.size());

        // Iterate to "c" and remove it, leaving the list empty.
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next());
        iterator.remove();
        assertEquals(0, backingList.size());

        // With no elements left, the iterator is exhausted and next() fails.
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
