package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping1 {

    /**
     * Verifies that a single-element list loops forever in both traversal
     * directions.
     */
    @Test
    void testLooping1() {
        final List<String> list = Arrays.asList("a");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
    }
}
