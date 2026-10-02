package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BoundedIterator#remove()} rejects a call made before
 * {@link BoundedIterator#next()} has ever been invoked.
 */
public class BoundedIteratorTest_testRemoveWithoutCallingNext {

    /** Backing data the bounded iterator decorates. */
    private static final List<String> SOURCE_ELEMENTS =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * Calling {@code remove()} on a freshly created iterator, without first
     * calling {@code next()}, must fail with an {@link IllegalStateException}
     * that explains the required call order.
     */
    @Test
    void testRemoveWithoutCallingNext() {
        final List<String> elements = new ArrayList<>(SOURCE_ELEMENTS);
        // Bound the iterator to start at index 1 and return at most 5 elements.
        final Iterator<String> boundedIterator =
                new BoundedIterator<>(elements.iterator(), 1, 5);

        final IllegalStateException thrown =
                assertThrows(IllegalStateException.class, () -> boundedIterator.remove());

        assertEquals("remove() cannot be called before calling next()", thrown.getMessage());
    }
}
