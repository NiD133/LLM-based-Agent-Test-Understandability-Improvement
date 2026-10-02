package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testEmptyIterator {

    /**
     * Returns an ObjectGraphIterator backed by an empty list, producing an iterator with no elements.
     */
    public ObjectGraphIterator<Object> makeEmptyIterator() {
        final ArrayList<Object> list = new ArrayList<>();
        return new ObjectGraphIterator<>(list.iterator());
    }

    public boolean supportsEmptyIterator() {
        return true;
    }

    public void verify() {
        // no additional verification
    }

    /**
     * Verifies that an ObjectGraphIterator over an empty collection:
     * - reports no elements via hasNext()
     * - throws NoSuchElementException when next() is called
     * - has a non-null toString() representation
     */
    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<Object> emptyIterator = makeEmptyIterator();

        assertFalse(emptyIterator.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(NoSuchElementException.class, () -> emptyIterator.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");

        verify();
        assertNotNull(emptyIterator.toString());
    }
}
