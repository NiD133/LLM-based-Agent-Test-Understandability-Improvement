package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests full-iteration behaviour of {@link BoundedIterator}.
 *
 * <p>The iterator under test wraps a 7-element list ["a".."g"] with offset=1 and
 * max=6, so it yields elements "b" through "g" (skipping the first element).</p>
 */
public class BoundedIteratorTest_testFullIterator {

    /** Source data shared across test fixtures. */
    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Returns a {@link BoundedIterator} that is non-empty: offset=1, max=(size-1).
     * Starts at the second element and covers all remaining elements.
     */
    private Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    /**
     * Returns a {@link BoundedIterator} over an empty source with max=10.
     * Useful as a sanity fixture; unused by {@code testFullIterator} but kept
     * for completeness in line with the original test class.
     */
    private Iterator<String> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<String>emptyList().iterator(), 0, 10);
    }

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    /**
     * Verifies that a non-empty {@link BoundedIterator}:
     * <ol>
     *   <li>reports {@code hasNext() == true} before any element is consumed,</li>
     *   <li>returns the first element without throwing,</li>
     *   <li>drains all remaining elements without error, and</li>
     *   <li>throws {@link NoSuchElementException} once the bounded range is exhausted.</li>
     * </ol>
     */
    @Test
    void testFullIterator() {
        final Iterator<String> it = makeObject();

        // The iterator must have at least one element to be a valid "full" iterator.
        assertTrue(it.hasNext(), "hasNext() should return true before the first call to next()");

        // Consuming the first element must not throw.
        assertDoesNotThrow(it::next, "next() must not throw for a non-empty BoundedIterator");

        // Drain the rest of the bounded range.
        while (it.hasNext()) {
            it.next();
        }

        // Once the bounded range is exhausted, next() must throw NoSuchElementException.
        assertThrows(
            NoSuchElementException.class,
            it::next,
            "next() must throw NoSuchElementException after the iterator is exhausted"
        );

        // toString() must return a non-null value (basic sanity check).
        assertNotNull(it.toString());
    }
}
