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
     * calling {@link LoopingListIterator#remove()} after each element drains the
     * wrapped list one element at a time, in order.
     *
     * <p>The list starts as ["a", "b", "c"]. Each iteration removes the element
     * that was just returned, shrinking the backing list until it is empty. Once
     * empty, the iterator reports no further elements and {@code next()} throws.</p>
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Remove "a": ["a", "b", "c"] -> ["b", "c"]
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        loop.remove();
        assertEquals(2, list.size());

        // Remove "b": ["b", "c"] -> ["c"]
        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());
        loop.remove();
        assertEquals(1, list.size());

        // Remove "c": ["c"] -> []
        assertTrue(loop.hasNext());
        assertEquals("c", loop.next());
        loop.remove();
        assertEquals(0, list.size());

        // The list is now empty, so there is nothing left to loop over.
        assertFalse(loop.hasNext());
        assertThrows(NoSuchElementException.class, () -> loop.next());
    }
}
