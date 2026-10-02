package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping2 {

    /**
     * Verifies that a looping iterator over the two-element list ["a", "b"]
     * wraps around in both directions:
     * <ul>
     *   <li>next() cycles a -> b -> a -> ... never running out of elements,</li>
     *   <li>after reset(), previous() cycles backwards b -> a -> b -> ...</li>
     * </ul>
     */
    @Test
    void testLooping2() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Move forward, looping past the end back to the first element.
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next()); // wrapped around to the start

        // Reset to the start, then move backward, looping past the
        // beginning around to the last element.
        loop.reset();

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous()); // wrapped around to the end

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous()); // wrapped around again
    }
}
