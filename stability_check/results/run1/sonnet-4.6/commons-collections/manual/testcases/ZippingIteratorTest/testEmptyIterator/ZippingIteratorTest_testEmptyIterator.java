package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link ZippingIterator} wrapping a single empty iterator
 * behaves correctly: {@code hasNext()} returns false, {@code next()} throws,
 * and {@code toString()} never returns null.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testEmptyIterator {

    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> fib;

    /** Returns a ZippingIterator that wraps a single empty iterator. */
    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    /** Returns a ZippingIterator that interleaves evens, odds, and fib. */
    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    @BeforeEach
    public void setUp() {
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
        // no additional cross-verification required
    }

    /**
     * Verifies that an empty ZippingIterator reports no elements, throws
     * {@link NoSuchElementException} on {@code next()}, and has a non-null
     * {@code toString()} representation.
     */
    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final ZippingIterator<Integer> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, () -> it.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");

        verify();
        assertNotNull(it.toString());
    }
}
