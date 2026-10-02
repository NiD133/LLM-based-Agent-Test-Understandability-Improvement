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

public class BoundedIteratorTest_testRemoveMiddle {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() throws Exception {
        testList = Arrays.asList(testArray);
    }

    /**
     * Verifies that removing the most recently returned bounded element removes
     * that same element from the underlying collection without changing the
     * remaining bounded iteration order.
     */
    @Test
    void testRemoveMiddle() {
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");

        iter.remove();
        assertFalse(testListCopy.contains("d"));

        assertNextElement(iter, "e");
        assertNextElement(iter, "f");
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    private void assertNextElement(final Iterator<String> iter, final String expectedElement) {
        assertTrue(iter.hasNext());
        assertEquals(expectedElement, iter.next());
    }
}
