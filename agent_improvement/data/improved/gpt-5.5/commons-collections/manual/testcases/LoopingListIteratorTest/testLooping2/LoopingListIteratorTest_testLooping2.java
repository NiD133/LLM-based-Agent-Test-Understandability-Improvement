package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping2 {

    /**
     * Verifies that a two-element iterator loops forward and backward.
     */
    @Test
    void testLooping2() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        loop.reset();

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
    }
}
