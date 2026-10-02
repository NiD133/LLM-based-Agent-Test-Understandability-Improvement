package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testAdd {

    /**
     * Tests the add method.
     */
    @Test
    void testAdd() {
        assertAddsBeforeNextElementWhileMovingForward();
        assertAddsAfterPreviousElementWhileMovingBackward();
    }

    private void assertAddsBeforeNextElementWhileMovingForward() {
        final LoopingListIterator<String> loop = iteratorStartingWith("b", "e", "f");

        loop.add("a");
        assertNext(loop, "b");

        loop.reset();
        assertNext(loop, "a");
        assertNext(loop, "b");

        loop.add("c");
        assertNext(loop, "e");
        assertPrevious(loop, "e");
        assertPrevious(loop, "c");
        assertNext(loop, "c");

        loop.add("d");
        loop.reset();

        assertNext(loop, "a");
        assertNext(loop, "b");
        assertNext(loop, "c");
        assertNext(loop, "d");
        assertNext(loop, "e");
        assertNext(loop, "f");
        assertNext(loop, "a");
    }

    private void assertAddsAfterPreviousElementWhileMovingBackward() {
        final LoopingListIterator<String> loop = iteratorStartingWith("b", "e", "f");

        loop.add("a");
        assertPrevious(loop, "a");

        loop.reset();
        assertPrevious(loop, "f");
        assertPrevious(loop, "e");

        loop.add("d");
        assertPrevious(loop, "d");

        loop.add("c");
        assertPrevious(loop, "c");

        loop.reset();
        assertNext(loop, "a");
        assertNext(loop, "b");
        assertNext(loop, "c");
        assertNext(loop, "d");
        assertNext(loop, "e");
        assertNext(loop, "f");
        assertNext(loop, "a");
    }

    private LoopingListIterator<String> iteratorStartingWith(final String first, final String second, final String third) {
        final List<String> list = new ArrayList<>(Arrays.asList(first, second, third));
        return new LoopingListIterator<>(list);
    }

    private void assertNext(final LoopingListIterator<String> loop, final String expected) {
        assertEquals(expected, loop.next());
    }

    private void assertPrevious(final LoopingListIterator<String> loop, final String expected) {
        assertEquals(expected, loop.previous());
    }
}
