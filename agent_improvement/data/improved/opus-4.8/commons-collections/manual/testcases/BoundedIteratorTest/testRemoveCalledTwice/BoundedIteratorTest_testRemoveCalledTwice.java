package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that calling {@link BoundedIterator#remove()} a second time, without
 * an intervening {@link BoundedIterator#next()}, is rejected with an
 * {@link IllegalStateException}.
 */
public class BoundedIteratorTest_testRemoveCalledTwice {

    /** Backing data: seven elements, "a" through "g". */
    private static final List<String> SOURCE_ELEMENTS =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @Test
    void testRemoveCalledTwice() {
        // BoundedIterator skips the first element (offset 1) and exposes up to 5 elements,
        // so the first element it returns is "b".
        final List<String> elements = new ArrayList<>(SOURCE_ELEMENTS);
        final Iterator<String> iterator = new BoundedIterator<>(elements.iterator(), 1, 5);

        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());

        // The first remove() is valid because next() was called immediately before it.
        iterator.remove();

        // A second remove() without another next() must fail.
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
