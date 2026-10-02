package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testNextAndPreviousIndex {

    /**
     * Tests nextIndex and previousIndex while moving forward and backward across
     * the loop boundary.
     */
    @Test
    void testNextAndPreviousIndex() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // <a> b c
        assertCursorIndexes(loop, 0, 2);

        // a <b> c
        assertNext(loop, "a");
        assertCursorIndexes(loop, 1, 0);

        // <a> b c
        assertPrevious(loop, "a");
        assertCursorIndexes(loop, 0, 2);

        // a b <c>
        assertPrevious(loop, "c");
        assertCursorIndexes(loop, 2, 1);

        // a <b> c
        assertPrevious(loop, "b");
        assertCursorIndexes(loop, 1, 0);

        // <a> b c
        assertPrevious(loop, "a");
        assertCursorIndexes(loop, 0, 2);
    }

    private static void assertCursorIndexes(final LoopingListIterator<String> iterator,
            final int expectedNextIndex, final int expectedPreviousIndex) {
        assertEquals(expectedNextIndex, iterator.nextIndex());
        assertEquals(expectedPreviousIndex, iterator.previousIndex());
    }

    private static void assertNext(final LoopingListIterator<String> iterator, final String expectedElement) {
        assertEquals(expectedElement, iterator.next());
    }

    private static void assertPrevious(final LoopingListIterator<String> iterator, final String expectedElement) {
        assertEquals(expectedElement, iterator.previous());
    }
}
