package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testJoggingNotOverBoundary {

    /**
     * Verifies that alternating between {@code next()} and {@code previous()}
     * stays within the physical list boundary until traversal actually reaches it.
     */
    @Test
    void testJoggingNotOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        loop.reset();

        assertEquals("a", loop.next());
        assertEquals("a", loop.previous());
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("b", loop.previous());
        assertEquals("b", loop.next());
    }
}
