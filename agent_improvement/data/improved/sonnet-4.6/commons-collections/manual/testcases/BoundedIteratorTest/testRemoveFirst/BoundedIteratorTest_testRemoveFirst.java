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

public class BoundedIteratorTest_testRemoveFirst {

    // Source data: 7 elements indexed 0–6 ("a" through "g")
    private static final String[] SOURCE_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = new ArrayList<>(Arrays.asList(SOURCE_ARRAY));
    }

    /**
     * Verifies that removing the first element yielded by a BoundedIterator
     * also removes it from the underlying collection, and that iteration
     * continues normally through the remaining bounded elements.
     *
     * <p>Setup: BoundedIterator with offset=1, max=5 over ["a","b","c","d","e","f","g"]
     * so the visible window is ["b","c","d","e","f"].
     * After calling next() to get "b" and then remove(), "b" should be gone from the
     * backing list, and the iterator should continue yielding "c","d","e","f" before
     * reporting hasNext()==false and throwing NoSuchElementException on a further next().
     */
    @Test
    void testRemoveFirst() {
        // Use a mutable copy so remove() can modify the backing list
        final List<String> mutableList = new ArrayList<>(testList);
        // offset=1 skips "a"; max=5 limits to ["b","c","d","e","f"]
        final Iterator<String> iter = new BoundedIterator<>(mutableList.iterator(), 1, 5);

        // Advance to the first element in the bounded window and remove it
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        iter.remove();
        assertFalse(mutableList.contains("b"), "\"b\" should have been removed from the backing list");

        // The remaining four elements should still be accessible
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        // Bounded window is exhausted; further iteration must signal end-of-sequence
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }
}
