package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

public class ZippingIteratorTest_testEmptyIterator {

    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    public boolean supportsEmptyIterator() {
        return true;
    }

    public void verify() {
        // No additional verification is required for this focused test case.
    }

    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<Integer> iterator = makeEmptyIterator();

        assertFalse(iterator.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(
                NoSuchElementException.class,
                () -> iterator.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");
        verify();
        assertNotNull(iterator.toString());
    }
}
