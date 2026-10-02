package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies how a {@link ZippingIterator} behaves when it wraps no elements at all.
 */
public class ZippingIteratorTest_testEmptyIterator {

    /**
     * Creates a {@link ZippingIterator} that has nothing to iterate over,
     * by wrapping a single empty child iterator.
     */
    private ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    @Test
    void testEmptyIterator() {
        final Iterator<Integer> emptyIterator = makeEmptyIterator();

        assertFalse(emptyIterator.hasNext(),
                "hasNext() should return false for an empty iterator");

        assertThrows(NoSuchElementException.class, emptyIterator::next,
                "next() must throw NoSuchElementException when the iterator is exhausted");

        assertNotNull(emptyIterator.toString(),
                "toString() should never return null");
    }
}
