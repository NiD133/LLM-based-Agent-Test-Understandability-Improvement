package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that a BoundedIterator with an offset larger than the underlying
 * collection size behaves as if there are no elements to return.
 */
public class BoundedIteratorTest_testOffsetGreaterThanSize {

    // A 7-element list used as the underlying data source
    private final List<String> testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * When the offset (10) exceeds the list size (7), the BoundedIterator
     * should immediately appear exhausted: hasNext() returns false and
     * next() throws NoSuchElementException.
     */
    @Test
    void testOffsetGreaterThanSize() {
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 10, 4);
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
