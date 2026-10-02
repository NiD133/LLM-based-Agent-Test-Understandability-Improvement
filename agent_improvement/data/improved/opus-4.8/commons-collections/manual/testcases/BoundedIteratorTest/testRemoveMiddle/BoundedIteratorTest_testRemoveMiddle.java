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

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedIterator#remove()} when removing an element from the
 * middle of the bounded range.
 */
public class BoundedIteratorTest_testRemoveMiddle {

    /** Source data: seven sequential letters. */
    private static final List<String> SOURCE =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * A BoundedIterator over offset 1, max 5 exposes the elements b, c, d, e, f.
     * Removing the middle element ("d") after reading it must delete that
     * element from the underlying list, while iteration continues normally over
     * the remaining bounded elements (e, f) before being exhausted.
     */
    @Test
    void testRemoveMiddle() {
        final List<String> backingList = new ArrayList<>(SOURCE);
        final Iterator<String> iter = new BoundedIterator<>(backingList.iterator(), 1, 5);

        // Read the first three bounded elements: b, c, d.
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());

        // Removing "d" must delete it from the underlying list.
        iter.remove();
        assertFalse(backingList.contains("d"));

        // Iteration continues over the remaining bounded elements: e, f.
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        // The bounded range is now exhausted.
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
