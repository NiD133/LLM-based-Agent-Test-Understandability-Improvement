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

public class LoopingListIteratorTest_testNextAndPreviousIndex {

    /**
     * Verifies that nextIndex() and previousIndex() report the correct cursor
     * position as the iterator navigates forward and backward through a 3-element
     * list, including the wrap-around behaviour at both ends.
     *
     * Notation used in step comments: the element that would be returned by the
     * next call to next() is enclosed in angle-brackets, e.g. [a] b c means the
     * cursor sits before index 0, so nextIndex()==0 and previousIndex()==2 (the
     * looped-back end of the list).
     */
    @Test
    void testNextAndPreviousIndex() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Initial state: cursor is before index 0 — [a] b c
        // previousIndex wraps to the last index (2) because the iterator loops.
        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());

        // Step forward: consume "a", cursor advances — a [b] c
        assertEquals("a", loop.next());
        assertEquals(1, loop.nextIndex());
        assertEquals(0, loop.previousIndex());

        // Step backward: unconsume "a", cursor retreats — [a] b c
        assertEquals("a", loop.previous());
        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());

        // Step backward past the beginning: loops to the last element "c" — a b [c]
        // previousIndex() returns 1 (index of "b") since "c" is at index 2 and
        // calling previous() again would return "b".
        assertEquals("c", loop.previous());
        assertEquals(2, loop.nextIndex());
        assertEquals(1, loop.previousIndex());

        // Step backward: consume "b" in reverse — a [b] c
        assertEquals("b", loop.previous());
        assertEquals(1, loop.nextIndex());
        assertEquals(0, loop.previousIndex());

        // Step backward: consume "a" in reverse — [a] b c
        // previousIndex wraps back to 2, the looped last index.
        assertEquals("a", loop.previous());
        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());
    }
}
