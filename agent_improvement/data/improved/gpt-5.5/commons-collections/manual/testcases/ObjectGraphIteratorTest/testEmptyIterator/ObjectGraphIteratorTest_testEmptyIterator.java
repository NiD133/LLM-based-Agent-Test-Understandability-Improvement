package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testEmptyIterator {

    public ObjectGraphIterator<Object> makeEmptyIterator() {
        final ArrayList<Object> list = new ArrayList<>();
        return new ObjectGraphIterator<>(list.iterator());
    }

    public boolean supportsEmptyIterator() {
        return true;
    }

    public void verify() {
        // Hook retained from the original iterator test template.
    }

    @Test
    void testEmptyIterator() {
        if (!supportsEmptyIterator()) {
            return;
        }

        final Iterator<Object> it = makeEmptyIterator();

        assertFalse(it.hasNext(), "hasNext() should return false for empty iterators");
        assertThrows(
                NoSuchElementException.class,
                it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        verify();
        assertNotNull(it.toString());
    }
}
