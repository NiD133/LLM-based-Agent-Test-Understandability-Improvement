package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.AbstractObjectTest;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromSingle {

    private ArrayList<Integer> evens;

    private ArrayList<Integer> odds;

    private ArrayList<Integer> fib;

    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    @BeforeEach
    public void setUp() throws Exception {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (0 == i % 2) {
                evens.add(i);
            } else {
                odds.add(i);
            }
        }
        fib = new ArrayList<>();
        fib.add(1);
        fib.add(1);
        fib.add(2);
        fib.add(3);
        fib.add(5);
        fib.add(8);
        fib.add(13);
        fib.add(21);
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

    @Test
    void testRemoveFromSingle() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        int expectedSize = evens.size();
        while (iter.hasNext()) {
            final Object o = iter.next();
            final Integer val = (Integer) o;
            if (val.intValue() % 4 == 0) {
                expectedSize--;
                iter.remove();
            }
        }
        assertEquals(expectedSize, evens.size());
    }
}
