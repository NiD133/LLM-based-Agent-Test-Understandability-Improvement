package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testReset {

    /**
     * Tests that reset always returns the iterator to the start of the wrapped list,
     * for both forward and reverse looping traversal.
     */
    @Test
    void testReset() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Advance away from the start, then verify reset makes the next value first again.
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        loop.reset();
        assertEquals("a", loop.next());

        // The same reset behavior should hold after another partial forward traversal.
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());

        // From the reset position, previous() loops to the end of the list.
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());

        // Repeated resets should keep returning previous() to the same end element.
        loop.reset();
        assertEquals("c", loop.previous());

        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());
        assertEquals("a", loop.previous());
    }
}
