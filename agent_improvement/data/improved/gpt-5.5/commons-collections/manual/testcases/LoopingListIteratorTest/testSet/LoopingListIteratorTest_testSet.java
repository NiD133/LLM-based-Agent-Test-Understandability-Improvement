package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testSet {

    /**
     * Verifies that set replaces the element returned by the most recent
     * previous or next call, including after the iterator has looped.
     */
    @Test
    void testSet() {
        final List<String> list = Arrays.asList("q", "r", "z");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Move backward from the start to the final element, then replace it.
        assertEquals("z", loop.previous());
        loop.set("c");

        // Replace the first two elements during forward traversal.
        loop.reset();
        assertEquals("q", loop.next());
        loop.set("a");
        assertEquals("r", loop.next());
        loop.set("b");

        // Confirm the iterator now loops through the updated contents.
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
    }
}
