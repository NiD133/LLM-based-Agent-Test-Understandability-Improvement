package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testEmptyIterator<E> {

    public Iterator<E> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<E>emptyList().iterator(), 0, 10);
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

        final Iterator<E> iterator = makeEmptyIterator();

        assertFalse(iterator.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(
            NoSuchElementException.class,
            () -> iterator.next(),
            "NoSuchElementException must be thrown when Iterator is exhausted");

        verify();
        assertNotNull(iterator.toString());
    }
}
