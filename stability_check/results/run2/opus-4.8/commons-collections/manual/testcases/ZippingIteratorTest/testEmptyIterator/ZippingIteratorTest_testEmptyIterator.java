package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link ZippingIterator} behaves when it wraps no elements at all.
 */
public class ZippingIteratorTest_testEmptyIterator {

    /**
     * Creates a {@link ZippingIterator} whose only child iterator is empty,
     * so the zipping iterator itself has nothing to return.
     *
     * @return an empty zipping iterator
     */
    private ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    /**
     * An empty iterator must report no elements, must throw when asked for the
     * next element, and must still provide a non-null string representation.
     */
    @Test
    void testEmptyIterator() {
        final Iterator<Integer> emptyIterator = makeEmptyIterator();

        assertFalse(emptyIterator.hasNext(),
                "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, emptyIterator::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(emptyIterator.toString());
    }
}
