package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testMaxGreaterThanSize {

    /** The source list of 7 elements that the bounded iterator decorates. */
    private final List<String> sourceList =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * When {@code max} is larger than the number of elements actually available
     * (offset 1, max 10, but only 6 elements remain after skipping the first),
     * the iterator should simply stop at the last element of the decorated
     * iterator instead of over-running it.
     */
    @Test
    void testMaxGreaterThanSize() {
        // Skip the first element ("a") and allow up to 10 elements.
        final Iterator<String> iter = new BoundedIterator<>(sourceList.iterator(), 1, 10);

        // Only the 6 remaining elements ("b" through "g") should be returned.
        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");
        assertNextElement(iter, "g");

        // The iterator is exhausted; further access must fail.
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    /**
     * Asserts that the iterator has a next element and that it equals the
     * expected value, then consumes it.
     */
    private static void assertNextElement(final Iterator<String> iter, final String expected) {
        assertTrue(iter.hasNext());
        assertEquals(expected, iter.next());
    }
}
