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

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Test a decorated iterator bounded to a {@code max} of 0. The
     * BoundedIterator should behave as if there are no more elements to return,
     * since it is technically an empty iterator.
     */
    @Test
    void testEmptyBounded() {
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 3, 0);

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
