package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testMaxGreaterThanSize {

    // 7-element list used as the underlying source for all tests in this class
    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * When {@code max} exceeds the number of remaining elements after the offset,
     * the iterator must stop at the natural end of the underlying collection rather
     * than throwing or silently truncating.
     *
     * Setup: offset=1 (skip "a"), max=10 (larger than the 6 remaining elements).
     * Expected traversal: b → c → d → e → f → g, then exhausted.
     */
    @Test
    void testMaxGreaterThanSize() {
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 1, 10);

        // Verify each of the 6 elements after the offset is returned in order
        final String[] expectedElements = { "b", "c", "d", "e", "f", "g" };
        for (final String expected : expectedElements) {
            assertTrue(iter.hasNext(), "Iterator should still have elements before returning '" + expected + "'");
            assertEquals(expected, iter.next());
        }

        // After all underlying elements are consumed the iterator must report exhaustion
        assertFalse(iter.hasNext(), "Iterator should be exhausted after all elements are consumed");
        assertThrows(NoSuchElementException.class, iter::next,
                "next() on an exhausted iterator must throw NoSuchElementException");
    }
}
