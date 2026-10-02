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
     * Verifies that removing elements while iterating forward shrinks the list correctly
     * and that once all elements are removed, hasNext() returns false and next() throws.
     *
     * Starting list: ["a", "b", "c"]
     * Step 1: advance to "a", remove it  -> list becomes ["b", "c"]
     * Step 2: advance to "b", remove it  -> list becomes ["c"]
     * Step 3: advance to "c", remove it  -> list becomes []
     * Final:  iterator is exhausted; next() must throw NoSuchElementException
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // List: ["a", "b", "c"] — iterator has elements to visit
        assertTrue(loop.hasNext());

        // Remove "a": advance past it, then remove; list shrinks to ["b", "c"]
        assertEquals("a", loop.next());
        loop.remove();
        assertEquals(2, list.size());
        assertTrue(loop.hasNext());

        // Remove "b": advance past it, then remove; list shrinks to ["c"]
        assertEquals("b", loop.next());
        loop.remove();
        assertEquals(1, list.size());
        assertTrue(loop.hasNext());

        // Remove "c": advance past it, then remove; list is now empty
        assertEquals("c", loop.next());
        loop.remove();
        assertEquals(0, list.size());

        // List is empty: hasNext() must be false and next() must throw
        assertFalse(loop.hasNext());
        assertThrows(NoSuchElementException.class, () -> loop.next());
    }
}
