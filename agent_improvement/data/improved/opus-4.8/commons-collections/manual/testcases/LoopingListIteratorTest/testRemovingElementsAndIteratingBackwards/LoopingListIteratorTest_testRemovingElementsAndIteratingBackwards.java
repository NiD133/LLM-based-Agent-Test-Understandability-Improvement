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

public class LoopingListIteratorTest_testRemovingElementsAndIteratingBackwards {

    /**
     * Verifies that iterating backwards with {@link LoopingListIterator#previous()}
     * and calling {@link LoopingListIterator#remove()} after each step empties the
     * underlying list one element at a time.
     *
     * <p>Starting from ["a", "b", "c"], a fresh iterator sits before the first
     * element, so the first {@code previous()} loops around to the last element "c".
     * Each removed element shrinks the list, and once every element has been removed
     * the iterator reports no previous element and throws on a further call.</p>
     */
    @Test
    void testRemovingElementsAndIteratingBackwards() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Step backwards from the start: loops around and returns the last element "c".
        assertTrue(loop.hasPrevious());
        assertEquals("c", loop.previous());

        // Remove "c"; list becomes ["a", "b"].
        loop.remove();
        assertEquals(2, list.size());

        // Next previous element is "b".
        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());

        // Remove "b"; list becomes ["a"].
        loop.remove();
        assertEquals(1, list.size());

        // The only remaining element is "a".
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        // Remove "a"; the list is now empty.
        loop.remove();
        assertEquals(0, list.size());

        // With nothing left to loop over, there is no previous element.
        assertFalse(loop.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> loop.previous());
    }
}
