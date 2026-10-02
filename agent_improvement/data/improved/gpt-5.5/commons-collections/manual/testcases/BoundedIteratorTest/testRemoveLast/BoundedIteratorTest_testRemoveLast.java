package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveLast {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    public Iterator<String> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<String>emptyList().iterator(), 0, 10);
    }

    public Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    public boolean supportsEmptyIterator() {
        return true;
    }

    public boolean supportsFullIterator() {
        return true;
    }

    public boolean supportsRemove() {
        return true;
    }

    public void verify() {
        // No cross-verification required for this isolated test case.
    }

    @Test
    void testRemoveLast() {
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

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

        assertFalse(iter.hasNext());
        final NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrown.getMessage());

        iter.remove();

        assertFalse(testListCopy.contains("f"));
        assertFalse(iter.hasNext());
        final NoSuchElementException thrownAfterRemove = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrownAfterRemove.getMessage());
    }
}
