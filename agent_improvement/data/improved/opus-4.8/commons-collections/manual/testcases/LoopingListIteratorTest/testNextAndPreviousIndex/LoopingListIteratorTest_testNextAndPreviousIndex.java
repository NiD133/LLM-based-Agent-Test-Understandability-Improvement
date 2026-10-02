package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testNextAndPreviousIndex {

    /**
     * Verifies that {@link LoopingListIterator#nextIndex()} and
     * {@link LoopingListIterator#previousIndex()} track the cursor position
     * correctly as it moves forward and backward, including when it wraps
     * around the ends of the list.
     *
     * <p>The list is {@code [a, b, c]}. The cursor sits <em>between</em>
     * elements; the brackets in each step show which element {@code next()}
     * would return. {@code nextIndex()} reports that element's index, and
     * {@code previousIndex()} reports the index of the element on the other
     * side of the cursor (wrapping to the far end of the list when needed).</p>
     */
    @Test
    void testNextAndPreviousIndex() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Cursor at start: [a] b c
        // next() -> a (index 0); wrapping back, previous() -> c (index 2)
        assertIndices(0, 2, loop);

        // Step forward over "a": a [b] c
        assertEquals("a", loop.next());
        assertIndices(1, 0, loop);

        // Step back over "a", returning to the start: [a] b c
        assertEquals("a", loop.previous());
        assertIndices(0, 2, loop);

        // Step back past the start, wrapping to the end: a b [c]
        assertEquals("c", loop.previous());
        assertIndices(2, 1, loop);

        // Step back over "c": a [b] c
        assertEquals("b", loop.previous());
        assertIndices(1, 0, loop);

        // Step back over "b", returning to the start: [a] b c
        assertEquals("a", loop.previous());
        assertIndices(0, 2, loop);
    }

    /**
     * Asserts that the iterator reports the expected indices for the elements
     * on either side of its current cursor position.
     *
     * @param expectedNextIndex     the index {@code nextIndex()} should return
     * @param expectedPreviousIndex the index {@code previousIndex()} should return
     * @param loop                  the iterator under test
     */
    private static void assertIndices(final int expectedNextIndex,
                                      final int expectedPreviousIndex,
                                      final LoopingListIterator<String> loop) {
        assertEquals(expectedNextIndex, loop.nextIndex(), "nextIndex()");
        assertEquals(expectedPreviousIndex, loop.previousIndex(), "previousIndex()");
    }
}
