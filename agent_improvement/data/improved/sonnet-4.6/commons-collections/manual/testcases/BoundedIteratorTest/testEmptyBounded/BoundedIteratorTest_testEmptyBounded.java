package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testEmptyBounded {

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    }

    /**
     * A BoundedIterator with max=0 should behave as empty regardless of the
     * underlying list contents: hasNext() returns false and next() throws.
     */
    @Test
    void testEmptyBounded() {
        // offset=3 skips the first three elements; max=0 allows no elements to be returned
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 3, 0);

        assertFalse(iter.hasNext(), "Iterator with max=0 should have no elements");
        assertThrows(NoSuchElementException.class, iter::next,
                "Calling next() on an exhausted iterator should throw NoSuchElementException");
    }
}
