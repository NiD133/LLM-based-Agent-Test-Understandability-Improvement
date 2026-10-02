package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that BoundedIterator.remove() correctly removes the last element returned
 * from the underlying collection when called in the middle of iteration.
 *
 * The bounded window used in the test covers elements at index 1 through 5
 * (offset=1, max=5), so the visible sequence is ["b", "c", "d", "e", "f"].
 */
public class BoundedIteratorTest_testRemoveMiddle {

    // Source data: seven-element list used to build each test fixture
    private static final String[] SOURCE_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> sourceList;

    @BeforeEach
    public void setUp() {
        sourceList = new ArrayList<>(Arrays.asList(SOURCE_ARRAY));
    }

    /**
     * Verifies that calling remove() on the middle element of a bounded window
     * removes that element from the backing collection and does not disturb the
     * remaining elements visible through the iterator.
     *
     * Bounded window: offset=1, max=5  →  visible elements: b, c, d, e, f
     * The element "d" (third in the window) is removed mid-iteration.
     */
    @Test
    void testRemoveMiddle() {
        // Use a mutable copy so that remove() can propagate to the backing list
        final List<String> mutableCopy = new ArrayList<>(sourceList);
        // BoundedIterator(iterator, offset=1, max=5): skips "a", then yields "b","c","d","e","f"
        final Iterator<String> boundedIter = new BoundedIterator<>(mutableCopy.iterator(), 1, 5);

        // Advance to "b"
        assertTrue(boundedIter.hasNext());
        assertEquals("b", boundedIter.next());

        // Advance to "c"
        assertTrue(boundedIter.hasNext());
        assertEquals("c", boundedIter.next());

        // Advance to "d", then remove it from the backing list
        assertTrue(boundedIter.hasNext());
        assertEquals("d", boundedIter.next());
        boundedIter.remove();
        assertFalse(mutableCopy.contains("d"), "\"d\" should have been removed from the backing list");

        // Continue iterating; the window still has "e" and "f" remaining
        assertTrue(boundedIter.hasNext());
        assertEquals("e", boundedIter.next());

        assertTrue(boundedIter.hasNext());
        assertEquals("f", boundedIter.next());

        // The bounded window is now exhausted
        assertFalse(boundedIter.hasNext());
        assertThrows(NoSuchElementException.class, boundedIter::next,
                "next() past the bounded window must throw NoSuchElementException");
    }
}
