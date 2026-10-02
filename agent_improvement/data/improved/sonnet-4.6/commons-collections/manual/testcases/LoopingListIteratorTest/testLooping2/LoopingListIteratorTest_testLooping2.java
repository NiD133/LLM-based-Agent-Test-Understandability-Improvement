package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping2 {

    /**
     * Tests that a LoopingListIterator over a two-element list cycles
     * continuously in both the forward and backward directions.
     *
     * <p>Forward pass: calling next() past the last element ("b") wraps
     * around and returns the first element ("a") again.
     *
     * <p>Backward pass: after reset(), calling previous() at the very start
     * of the list wraps around and returns the last element ("b").
     */
    @Test
    void testLooping2() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // --- Forward iteration: verifies that next() wraps from the end back to "a" ---

        // Cursor starts before "a" (index 0).
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());  // cursor advances to before "b"

        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());  // cursor reaches the end of the list

        // At the end of the list, next() wraps around and returns the first element again.
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());  // looped: cursor advances to before "b"

        // --- Backward iteration: verifies that previous() wraps from the start back to "b" ---

        // Reset moves the cursor back to the very beginning (before "a", index 0).
        loop.reset();

        // At the start of the list, previous() wraps around and returns the last element.
        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous()); // looped: cursor is now before "b" (index 1)

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous()); // cursor moves back to index 0

        // At index 0 again, previous() wraps around once more and returns "b".
        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous()); // looped again
    }
}
