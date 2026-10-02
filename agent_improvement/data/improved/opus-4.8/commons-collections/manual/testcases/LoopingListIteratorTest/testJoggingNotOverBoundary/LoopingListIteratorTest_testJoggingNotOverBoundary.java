package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testJoggingNotOverBoundary {

    /**
     * Tests jogging the cursor back and forth between the two elements of the
     * list {@code ["a", "b"]} without ever crossing the begin/end boundary
     * (so the looping/wrap-around behaviour is never triggered).
     *
     * <p>The comment after each call shows where the cursor sits, using
     * {@code |} to mark the cursor position. For example {@code a | b} means
     * the cursor is between "a" and "b": {@code next()} would return "b" and
     * {@code previous()} would return "a".</p>
     */
    @Test
    void testJoggingNotOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Start at the beginning of the list.
        loop.reset();
        // Cursor: | a   b

        // Move forward over "a", then back over it, then forward again.
        assertEquals("a", loop.next());      // Cursor: a | b
        assertEquals("a", loop.previous());  // Cursor: | a   b
        assertEquals("a", loop.next());      // Cursor: a | b

        // Move forward over "b", then back over it, then forward again.
        assertEquals("b", loop.next());      // Cursor: a   b |
        assertEquals("b", loop.previous());  // Cursor: a | b
        assertEquals("b", loop.next());      // Cursor: a   b |
    }
}
