package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testReset {

    /**
     * Verifies that {@link LoopingListIterator#reset()} always returns the
     * cursor to the start of the list, regardless of how far it has been
     * advanced with {@link LoopingListIterator#next()} or rewound with
     * {@link LoopingListIterator#previous()}.
     *
     * <p>The cursor sits between elements. The notation {@code [a] b c} below
     * marks the cursor position: it is just before {@code a}, so the next call
     * to {@code next()} returns {@code a} and the next call to
     * {@code previous()} wraps around to the last element {@code c}.</p>
     */
    @Test
    void testReset() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);
        // Cursor starts at the beginning: [a] b c

        // Advance two steps: a b [c]
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());

        // reset() jumps the cursor back to the beginning: [a] b c
        loop.reset();
        assertEquals("a", loop.next());

        // reset() again, then walk forward through the whole list and loop around.
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());

        // reset() puts us back at the start; previous() now wraps to the end.
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());

        // reset() again; previous() wraps to the end once more.
        loop.reset();
        assertEquals("c", loop.previous());

        // Final reset(), then walk backward through the whole list.
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());
        assertEquals("a", loop.previous());
    }
}
