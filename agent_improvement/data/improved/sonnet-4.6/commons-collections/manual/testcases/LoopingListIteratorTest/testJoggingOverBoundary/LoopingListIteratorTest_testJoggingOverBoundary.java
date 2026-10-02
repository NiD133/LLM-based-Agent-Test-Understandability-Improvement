package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testJoggingOverBoundary {

    /**
     * Tests jogging back and forth between two elements over the begin/end
     * boundary of the list.
     *
     * <p>Cursor notation used in comments: {@code ^} marks the cursor position
     * within the list. For example, {@code [^a b]} means the cursor is before
     * "a" (at index 0); {@code [a^b]} means the cursor is between "a" and "b"
     * (at index 1); {@code [ab^]} means the cursor is past the end (at index 2).
     * Because the iterator loops, {@code [ab^]} and {@code [^a b]} behave
     * identically for the next navigation call.</p>
     */
    @Test
    void testJoggingOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");

        // Cursor starts at the beginning of the list: [^a b]
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // previous() at the begin boundary wraps to the last element.
        // Returns "b" and leaves cursor between "a" and "b": [a^b]
        assertEquals("b", loop.previous());

        // next() at [a^b] advances past "b", reaching the end: [ab^]
        assertEquals("b", loop.next());

        // previous() at the end boundary [ab^] steps back to "b": [a^b]
        assertEquals("b", loop.previous());

        // previous() at [a^b] steps back further to "a", crossing to the start: [^a b]
        assertEquals("a", loop.previous());

        // next() at [^a b] advances to "a", moving cursor right: [a^b]
        assertEquals("a", loop.next());

        // previous() at [a^b] steps back to "a", returning cursor to start: [^a b]
        assertEquals("a", loop.previous());
    }
}
