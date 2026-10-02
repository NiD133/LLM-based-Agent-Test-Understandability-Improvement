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
     * Creates a ZippingIterator wrapping a single empty iterator, producing an
     * iterator that has no elements.
     */
    @SuppressWarnings("unchecked")
    private ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    /** Returns true; this test class always exercises the empty-iterator path. */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /** Hook for subclass cross-verification; no-op here. */
    public void verify() {
        // do nothing
    }

    /**
     * Verifies that a ZippingIterator over an empty source:
     * <ul>
     *   <li>reports {@code hasNext() == false}</li>
     *   <li>throws {@link NoSuchElementException} on {@code next()}</li>
     *   <li>has a non-null {@code toString()} representation</li>
     * </ul>
     */
    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<Integer> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, () -> it.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");
        verify();
        assertNotNull(it.toString());
    }
}
