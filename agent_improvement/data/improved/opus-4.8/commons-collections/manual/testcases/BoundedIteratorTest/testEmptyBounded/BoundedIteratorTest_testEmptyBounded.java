package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link BoundedIterator} created with a {@code max} of 0
 * behaves like an empty iterator, regardless of how many elements the
 * decorated iterator holds.
 */
public class BoundedIteratorTest_testEmptyBounded {

    /** Backing data for the iterator under test. */
    private final List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * A {@code max} of 0 means no elements may be returned, so the bounded
     * iterator must report no next element and throw when {@code next()} is called.
     */
    @Test
    void testEmptyBounded() {
        final Iterator<String> boundedIterator =
                new BoundedIterator<>(elements.iterator(), 3, 0);

        assertFalse(boundedIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> boundedIterator.next());
    }
}
