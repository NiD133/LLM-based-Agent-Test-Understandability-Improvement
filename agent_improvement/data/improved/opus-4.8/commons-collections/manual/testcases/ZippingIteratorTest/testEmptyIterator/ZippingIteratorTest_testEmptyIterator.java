package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests how a {@link ZippingIterator} behaves when it wraps an empty iterator,
 * i.e. when it has no elements to interleave.
 */
public class ZippingIteratorTest_testEmptyIterator {

    /**
     * Creates a {@link ZippingIterator} that has nothing to iterate over,
     * by zipping a single empty iterator.
     *
     * @return an empty zipping iterator
     */
    private ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    @Test
    void testEmptyIterator() {
        final Iterator<Integer> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
