package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveLast {

    /** Source elements used to build the iterator under test. */
    private static final String[] SOURCE_ELEMENTS = { "a", "b", "c", "d", "e", "f", "g" };

    /**
     * A {@link BoundedIterator} that starts at offset 1 and returns at most 5
     * elements therefore exposes the window "b", "c", "d", "e", "f". This test
     * walks the iterator to its end, then removes the last returned element
     * ("f") and verifies it is deleted from the backing list while the iterator
     * remains exhausted.
     */
    @Test
    void testRemoveLast() {
        final List<String> backingList = new ArrayList<>(Arrays.asList(SOURCE_ELEMENTS));
        final Iterator<String> iter = new BoundedIterator<>(backingList.iterator(), 1, 5);

        // The bounded window is "b" through "f"; "a" (offset) and "g" (beyond max) are excluded.
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        // Window is exhausted: hasNext() is false and next() throws.
        assertFalse(iter.hasNext());
        final NoSuchElementException afterLast =
                assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(afterLast.getMessage());

        // remove() deletes the last element returned ("f") from the backing list.
        iter.remove();
        assertFalse(backingList.contains("f"));

        // The iterator stays exhausted after the removal.
        assertFalse(iter.hasNext());
        final NoSuchElementException afterRemove =
                assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(afterRemove.getMessage());
    }
}
