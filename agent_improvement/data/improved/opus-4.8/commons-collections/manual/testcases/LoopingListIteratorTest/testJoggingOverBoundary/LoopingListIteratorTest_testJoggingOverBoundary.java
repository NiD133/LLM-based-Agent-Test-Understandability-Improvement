package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link LoopingListIterator} can jog back and forth between
 * two adjacent elements when that movement crosses the list's begin/end
 * boundary.
 * <p>
 * The iterator wraps the two-element list {@code [a, b]}. Conceptually the
 * cursor sits between elements; in the comments below the bracketed element
 * marks where the cursor currently rests, e.g. {@code <a> b} means the cursor
 * is positioned just before {@code "a"}.
 * </p>
 */
public class LoopingListIteratorTest_testJoggingOverBoundary {

    @Test
    void testJoggingOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");

        // Cursor starts at the beginning of the list:  <a> b
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // previous() at the beginning loops around to the last element "b":  a <b>
        assertEquals("b", loop.previous());

        // next() loops back past the end to "b" again:  <a> b
        assertEquals("b", loop.next());

        // previous() returns "b" once more:  a <b>
        assertEquals("b", loop.previous());

        // previous() now steps to the earlier element "a":  <a> b
        assertEquals("a", loop.previous());

        // next() returns "a":  a <b>
        assertEquals("a", loop.next());

        // previous() returns "a" again:  <a> b
        assertEquals("a", loop.previous());
    }
}
