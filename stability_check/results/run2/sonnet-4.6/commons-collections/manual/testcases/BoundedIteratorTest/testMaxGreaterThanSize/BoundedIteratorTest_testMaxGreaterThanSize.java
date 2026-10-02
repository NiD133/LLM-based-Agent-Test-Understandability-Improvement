package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a BoundedIterator with a max larger than the underlying collection's
 * size iterates to the end of the collection without error.
 */
public class BoundedIteratorTest_testMaxGreaterThanSize {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * When {@code max} exceeds the number of remaining elements, the iterator
     * should exhaust the underlying collection and then report hasNext() == false,
     * throwing NoSuchElementException on any further call to next().
     *
     * The list has 7 elements; offset=1 skips "a", and max=10 is deliberately
     * larger than the 6 remaining elements to verify the boundary behaviour.
     */
    @Test
    void testMaxGreaterThanSize() {
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 1, 10);

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

        assertTrue(iter.hasNext());
        assertEquals("g", iter.next());

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
