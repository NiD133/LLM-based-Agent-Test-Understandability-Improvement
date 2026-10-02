package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testJoggingOverBoundary {

    /**
     * Tests jogging back and forth between two elements over the begin/end
     * boundary of the list.
     */
    @Test
    void testJoggingOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertEquals("b", loop.previous(), "previous() from the start wraps to the last element");
        assertEquals("b", loop.next(), "next() returns to the last element after the wrapped previous() call");
        assertEquals("b", loop.previous(), "previous() returns to the last element without crossing the boundary");

        assertEquals("a", loop.previous(), "previous() moves from the last element back to the first element");
        assertEquals("a", loop.next(), "next() returns to the first element after stepping backward");
        assertEquals("a", loop.previous(), "previous() returns to the first element without crossing the boundary");
    }
}
