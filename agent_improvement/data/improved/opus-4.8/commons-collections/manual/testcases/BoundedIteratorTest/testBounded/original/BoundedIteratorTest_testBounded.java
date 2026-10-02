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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.collections4.AbstractObjectTest;
import org.apache.commons.collections4.IteratorUtils;

public class BoundedIteratorTest_testBounded {

    /**
     * Test array of size 7
     */
    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<E> testList;

    public Iterator<E> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<E>emptyList().iterator(), 0, 10);
    }

    public Iterator<E> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() throws Exception {
        testList = Arrays.asList((E[]) testArray);
    }

    /**
     * Implement this method to return an iterator over an empty collection.
     *
     * @return an empty iterator
     */
    private Iterator<E> __super_makeEmptyIterator();

    /**
     * Implements the abstract superclass method to return the full iterator.
     *
     * @return a full iterator
     */
    private Iterator<E> __super_makeObject();

    /**
     * Tests whether or not we are testing an iterator that can be empty.
     * Default is true.
     *
     * @return true if Iterator can be empty
     */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /**
     * Tests whether or not we are testing an iterator that can contain elements.
     * Default is true.
     *
     * @return true if Iterator can be full
     */
    public boolean supportsFullIterator() {
        return true;
    }

    /**
     * Tests whether or not we are testing an iterator that supports remove().
     * Default is true.
     *
     * @return true if Iterator supports remove
     */
    public boolean supportsRemove() {
        return true;
    }

    /**
     * Allows subclasses to add complex cross verification
     */
    public void verify() {
        // do nothing
    }

    /**
     * Test a decorated iterator bounded such that the first element returned is
     * at an index greater its first element, and the last element returned is
     * at an index less than its last element.
     */
    @Test
    void testBounded() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 2, 4);
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next(), "Expected NoSuchElementException.");
    }
}
