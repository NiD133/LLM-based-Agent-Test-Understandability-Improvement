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

    private static final long OFFSET_TO_FIRST_RETURNED_ELEMENT = 1;
    private static final long MAXIMUM_RETURNED_ELEMENTS = 5;

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    }

    /**
     * Tests removing the first element returned by the bounded iterator.
     * The removed value must also disappear from the underlying collection.
     */
    @Test
    void testRemoveFirst() {
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(
                testListCopy.iterator(),
                OFFSET_TO_FIRST_RETURNED_ELEMENT,
                MAXIMUM_RETURNED_ELEMENTS);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        iter.remove();
        assertFalse(testListCopy.contains("b"));

        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
