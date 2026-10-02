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

/**
 * Tests {@link BoundedIterator} for the case where the {@code max} bound is
 * greater than the number of available elements in the decorated iterator.
 * <p>
 * When {@code max} exceeds the remaining size, the bounded iterator should
 * simply stop once the underlying iterator is exhausted, so its last returned
 * element matches the last element of the decorated iterator.
 * </p>
 */
public class BoundedIteratorTest_testMaxGreaterThanSize {

    /** Source elements decorated by the iterator under test. */
    private final List<String> source = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * With an offset of 1 and a max of 10 over a 7-element list, the iterator
     * should skip "a" and then return every remaining element ("b".."g")
     * before reporting exhaustion, because max is larger than what remains.
     */
    @Test
    void testMaxGreaterThanSize() {
        // Skip the first element (offset = 1); max = 10 is deliberately larger
        // than the 6 elements that remain after the offset.
        final Iterator<String> iter = new BoundedIterator<>(source.iterator(), 1, 10);

        for (final String expected : new String[] { "b", "c", "d", "e", "f", "g" }) {
            assertTrue(iter.hasNext());
            assertEquals(expected, iter.next());
        }

        // The underlying iterator is exhausted even though max was not reached.
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
