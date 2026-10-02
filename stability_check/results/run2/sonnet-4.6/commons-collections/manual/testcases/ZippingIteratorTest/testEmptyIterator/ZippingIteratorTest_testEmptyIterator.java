package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testEmptyIterator {

    /**
     * Creates a ZippingIterator wrapping a single empty iterator, representing
     * the minimum case: nothing to iterate over.
     */
    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    /**
     * Whether the iterator under test supports being empty.
     * Always true for ZippingIterator.
     */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /**
     * Hook for subclasses to add cross-verification logic after each test step.
     * No-op by default.
     */
    public void verify() {
        // do nothing
    }

    /**
     * Verifies that an empty ZippingIterator correctly reports no elements and
     * throws NoSuchElementException when next() is called.
     */
    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<Integer> it = makeEmptyIterator();

        // An empty iterator should immediately report no more elements
        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");

        // Calling next() on an exhausted iterator must throw NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> it.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");

        verify();
        assertNotNull(it.toString());
    }
}
