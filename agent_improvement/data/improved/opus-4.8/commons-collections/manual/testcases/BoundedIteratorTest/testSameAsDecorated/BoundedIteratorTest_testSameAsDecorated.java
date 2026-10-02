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
 * Tests that a {@link BoundedIterator} configured with {@code offset == 0} and
 * {@code max == size} behaves exactly like the iterator it decorates: it returns
 * every element, in order, and then signals exhaustion.
 */
public class BoundedIteratorTest_testSameAsDecorated {

    /** The elements backing the iterator under test. */
    private final List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @Test
    void testSameAsDecorated() {
        // offset 0 and max == size means nothing is bounded out.
        final Iterator<String> iter =
                new BoundedIterator<>(elements.iterator(), 0, elements.size());

        // Every element of the decorated iterator should be returned, in order.
        for (final String expected : elements) {
            assertTrue(iter.hasNext());
            assertEquals(expected, iter.next());
        }

        // After the last element the iterator is exhausted.
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
