package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testReset {

    private static final List<String> THREE_ELEMENTS = Arrays.asList("a", "b", "c");

    /**
     * Verifies that {@code reset()} repositions the iterator to the beginning of the list
     * during forward traversal. After a reset, the next call to {@code next()} returns
     * the first element regardless of how far into the list the iterator had advanced.
     */
    @Test
    void resetDuringForwardTraversal_repositionsToListStart() {
        LoopingListIterator<String> loop = new LoopingListIterator<>(THREE_ELEMENTS);

        // Advance partially through the list
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());

        // reset() repositions to index 0: next() returns first element
        loop.reset();
        assertEquals("a", loop.next());

        // reset() is consistent: another reset still repositions to first element
        loop.reset();
        assertEquals("a", loop.next());

        // After reset, full forward traversal proceeds normally
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
    }

    /**
     * Verifies that {@code reset()} repositions the iterator to the beginning of the list
     * during backward traversal. After a reset, the next call to {@code previous()} wraps
     * around and returns the last element, because the iterator cursor is now at index 0.
     */
    @Test
    void resetDuringBackwardTraversal_repositionsToListStart() {
        LoopingListIterator<String> loop = new LoopingListIterator<>(THREE_ELEMENTS);

        // At index 0 after reset, previous() wraps around to return the last element
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());

        // reset() during backward traversal repositions to index 0
        loop.reset();
        assertEquals("c", loop.previous());

        // Multiple consecutive resets all reposition to index 0
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());
        assertEquals("a", loop.previous());
    }
}
