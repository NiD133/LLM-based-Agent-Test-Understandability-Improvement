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

    /** Source data of 7 elements that the iterator decorates. */
    private final List<String> sourceList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * When {@code max} is larger than the number of remaining elements, the
     * {@link BoundedIterator} should simply stop at the end of the decorated
     * iterator rather than fail.
     *
     * <p>Here we skip the first element (offset = 1) and allow up to 10
     * elements (max = 10). Since only 6 elements remain after the offset,
     * the iterator should return "b" through "g" and then be exhausted.</p>
     */
    @Test
    void testMaxGreaterThanSize() {
        final int offset = 1;
        final int max = 10;
        final Iterator<String> iter = new BoundedIterator<>(sourceList.iterator(), offset, max);

        // Every element from the offset to the end of the source is returned, in order.
        for (final String expected : new String[] { "b", "c", "d", "e", "f", "g" }) {
            assertTrue(iter.hasNext());
            assertEquals(expected, iter.next());
        }

        // The source is exhausted even though max was never reached.
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
