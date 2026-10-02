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

public class LoopingListIteratorTest_testRemovingElementsAndIteratingForward {

    /**
     * Verifies that iterating forward with {@link LoopingListIterator#next()} and
     * deleting each visited element with {@link LoopingListIterator#remove()}
     * drains the wrapped list one element at a time.
     *
     * <p>Starting from ["a", "b", "c"], the test repeatedly reads the next
     * element and removes it, checking that the backing list shrinks and that
     * {@code hasNext()} reflects the remaining elements. Once the list is empty,
     * {@code hasNext()} must report {@code false} and {@code next()} must throw
     * {@link NoSuchElementException}.</p>
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> backingList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loopingIterator = new LoopingListIterator<>(backingList);

        // Read and remove "a"; list shrinks to ["b", "c"].
        assertTrue(loopingIterator.hasNext());
        assertEquals("a", loopingIterator.next());
        loopingIterator.remove();
        assertEquals(2, backingList.size());

        // Read and remove "b"; list shrinks to ["c"].
        assertTrue(loopingIterator.hasNext());
        assertEquals("b", loopingIterator.next());
        loopingIterator.remove();
        assertEquals(1, backingList.size());

        // Read and remove "c"; list becomes empty.
        assertTrue(loopingIterator.hasNext());
        assertEquals("c", loopingIterator.next());
        loopingIterator.remove();
        assertEquals(0, backingList.size());

        // With nothing left to loop over, iteration reports exhaustion.
        assertFalse(loopingIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> loopingIterator.next());
    }
}
