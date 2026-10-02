package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testJoggingNotOverBoundary {

    /**
     * Verifies that calling next() and previous() alternately stays within the list
     * boundaries and never wraps around to the other end.
     *
     * The list is ["a", "b"].  Cursor positions are shown as "|":
     *   "| a b"  — before "a" (start of list)
     *   "a | b"  — between "a" and "b"
     *   "a b |"  — after "b" (end of list)
     */
    @Test
    void testJoggingNotOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        loop.reset(); // cursor: | a b

        // Phase 1: jog between "a" and "b" starting from the front
        assertEquals("a", loop.next());     // cursor: a | b
        assertEquals("a", loop.previous()); // cursor: | a b
        assertEquals("a", loop.next());     // cursor: a | b

        // Phase 2: advance to "b", then jog back and forth around it
        assertEquals("b", loop.next());     // cursor: a b |
        assertEquals("b", loop.previous()); // cursor: a | b
        assertEquals("b", loop.next());     // cursor: a b |
    }
}
