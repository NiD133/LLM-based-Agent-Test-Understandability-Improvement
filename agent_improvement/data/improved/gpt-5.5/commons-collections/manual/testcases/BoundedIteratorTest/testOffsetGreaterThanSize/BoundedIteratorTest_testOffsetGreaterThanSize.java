package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testOffsetGreaterThanSize {

    private static final String[] TEST_VALUES = { "a", "b", "c", "d", "e", "f", "g" };
    private static final long OFFSET_GREATER_THAN_SIZE = 10;
    private static final long MAXIMUM_ELEMENTS = 4;

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_VALUES);
    }

    /**
     * If the offset is greater than the decorated iterator's size, the bounded
     * iterator has no elements left to return.
     */
    @Test
    void testOffsetGreaterThanSize() {
        final Iterator<String> iter = new BoundedIterator<>(
                testList.iterator(),
                OFFSET_GREATER_THAN_SIZE,
                MAXIMUM_ELEMENTS);

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
