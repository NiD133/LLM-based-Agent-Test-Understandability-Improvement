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
     * Verifies that elements can be removed one by one while iterating forward,
     * and that the iterator correctly reflects the shrinking list after each removal.
     *
     * The test iterates through ["a", "b", "c"], removing each element immediately
     * after it is returned by next(). After each removal the list size decreases by
     * one and hasNext() stays true as long as the list is non-empty. Once all
     * elements are gone, hasNext() returns false and next() throws
     * NoSuchElementException — the looping behaviour that would otherwise wrap
     * around is suppressed because the underlying list is now empty.
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // --- Step 1: advance to "a" and remove it ---
        // list: ["a", "b", "c"]  →  hasNext() is true because the list is non-empty
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        loop.remove();
        // list: ["b", "c"]  →  size drops to 2
        assertEquals(2, list.size());

        // --- Step 2: advance to "b" and remove it ---
        // hasNext() is still true because "b" and "c" remain
        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());
        loop.remove();
        // list: ["c"]  →  size drops to 1
        assertEquals(1, list.size());

        // --- Step 3: advance to "c" and remove it ---
        // hasNext() is still true because "c" remains
        assertTrue(loop.hasNext());
        assertEquals("c", loop.next());
        loop.remove();
        // list: []  →  size drops to 0
        assertEquals(0, list.size());

        // --- Step 4: verify exhausted-iterator behaviour ---
        // With an empty underlying list the looping iterator has nowhere to loop;
        // hasNext() must return false and next() must throw NoSuchElementException.
        assertFalse(loop.hasNext());
        assertThrows(NoSuchElementException.class, () -> loop.next());
    }
}
