package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testFullIterator {

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
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
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
        // do nothing
    }

    /**
     * Tests normal iteration behavior: the iterator must have at least one element,
     * must traverse all elements without error, and must throw NoSuchElementException
     * once exhausted.
     */
    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<Integer> it = makeObject();

        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        while (it.hasNext()) {
            it.next();
            verify();
        }

        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when iterator is exhausted");
        assertNotNull(it.toString());
    }
}
