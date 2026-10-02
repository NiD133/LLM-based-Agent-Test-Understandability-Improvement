package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LoopingListIterator} loops endlessly in both
 * directions when the underlying list holds a single element.
 */
public class LoopingListIteratorTest_testLooping1 {

    /** The only element in the list under test. */
    private static final String ONLY_ELEMENT = "a";

    /**
     * With a single-element list {@code ["a"]}, the iterator should never
     * run out of elements: every {@code next()} and {@code previous()} call
     * must return that same element, no matter how many times we wrap around.
     */
    @Test
    void testLooping1() {
        final List<String> singleElementList = Arrays.asList(ONLY_ELEMENT);
        final LoopingListIterator<String> loop = new LoopingListIterator<>(singleElementList);

        // Walk forward three times; the iterator keeps looping back to "a".
        for (int forwardStep = 0; forwardStep < 3; forwardStep++) {
            assertTrue(loop.hasNext(), "single-element loop always has a next element");
            assertEquals(ONLY_ELEMENT, loop.next());
        }

        // Walk backward three times; looping back to "a" works in reverse too.
        for (int backwardStep = 0; backwardStep < 3; backwardStep++) {
            assertTrue(loop.hasPrevious(), "single-element loop always has a previous element");
            assertEquals(ONLY_ELEMENT, loop.previous());
        }
    }
}
