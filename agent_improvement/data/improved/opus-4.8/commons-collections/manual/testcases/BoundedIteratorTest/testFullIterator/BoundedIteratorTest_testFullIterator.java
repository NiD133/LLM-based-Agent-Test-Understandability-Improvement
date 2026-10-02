package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests normal forward iteration over a {@link BoundedIterator}.
 */
public class BoundedIteratorTest_testFullIterator {

    /** Source data: seven single-letter strings. */
    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Creates a BoundedIterator that skips the first element (offset = 1) and
     * returns the remaining {@code size - 1} elements, i.e. "b" through "g".
     */
    private Iterator<String> makeBoundedIterator() {
        return new BoundedIterator<>(testList.iterator(), 1, testList.size() - 1);
    }

    @Test
    void testFullIterator() {
        final Iterator<String> it = makeBoundedIterator();

        // The bounded range is non-empty, so the first element must be available.
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Consume every remaining element in the bounded range.
        while (it.hasNext()) {
            it.next();
        }

        // Once exhausted, next() must signal that no more elements remain.
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
