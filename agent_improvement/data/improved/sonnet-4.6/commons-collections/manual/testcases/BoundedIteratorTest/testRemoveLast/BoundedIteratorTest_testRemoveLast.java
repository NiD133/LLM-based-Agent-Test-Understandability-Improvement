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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveLast {

    // Source data: indices 0-6 → ["a","b","c","d","e","f","g"]
    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Verifies that calling remove() after consuming the last element of a
     * bounded range removes that element from the underlying collection, and
     * that the iterator correctly reports exhaustion afterwards.
     *
     * Iterator configured with offset=1 and max=5, so it visits:
     * "b", "c", "d", "e", "f" — the last visited element "f" should be removed.
     */
    @Test
    void testRemoveLast() {
        // Use a mutable copy so remove() can actually modify the backing list
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        // Consume all five elements in the bounded range [1, 1+5)
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

        // Iterator is now exhausted — hasNext() must be false and next() must throw
        assertFalse(iter.hasNext());
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrown.getMessage());

        // remove() operates on the last element returned ("f")
        iter.remove();
        assertFalse(testListCopy.contains("f"));

        // Iterator remains exhausted after the remove
        assertFalse(iter.hasNext());
        NoSuchElementException thrown1 = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrown1.getMessage());
    }
}
