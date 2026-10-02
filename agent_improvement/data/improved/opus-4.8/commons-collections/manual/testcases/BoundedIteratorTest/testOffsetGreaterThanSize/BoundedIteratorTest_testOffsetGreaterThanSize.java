package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link BoundedIterator} behaves when the requested {@code offset}
 * is larger than the number of elements available in the decorated iterator.
 *
 * <p>The decorated iterator only has 7 elements, but the bounded view is asked
 * to skip the first 10. Because there is nothing left to return after skipping
 * past the end, the iterator must report that it is exhausted.</p>
 */
public class BoundedIteratorTest_testOffsetGreaterThanSize {

    /** Source data: 7 elements, indices 0..6. */
    private final List<String> sourceElements =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @Test
    void offsetBeyondSourceSizeYieldsNoElements() {
        // offset (10) is greater than the source size (7), so every element is skipped.
        final long offsetPastEnd = 10;
        final long maxElements = 4;
        final Iterator<String> boundedIterator =
                new BoundedIterator<>(sourceElements.iterator(), offsetPastEnd, maxElements);

        assertFalse(boundedIterator.hasNext(),
                "no elements remain once the offset skips past the end of the source");
        assertThrows(NoSuchElementException.class, boundedIterator::next,
                "next() must fail because the bounded view is empty");
    }
}
