package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the normal, full-iteration behaviour of {@link ObjectGraphIterator}.
 *
 * <p>The iterator under test wraps an "iterator of iterators". When asked for the
 * next element it transparently descends into each nested iterator in turn, so the
 * six values below are yielded as a single flat sequence:
 * "One", "Two", "Three" (from list1), "Four" (from list2), "Five", "Six" (from list3).</p>
 */
public class ObjectGraphIteratorTest_testFullIterator {

    /** Nested iterators that, drained in order, yield six String elements. */
    private List<Iterator<String>> nestedIterators;

    @BeforeEach
    public void setUp() {
        final List<String> list1 = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        final List<String> list2 = new ArrayList<>(Arrays.asList("Four"));
        final List<String> list3 = new ArrayList<>(Arrays.asList("Five", "Six"));

        nestedIterators = new ArrayList<>();
        nestedIterators.add(list1.iterator());
        nestedIterators.add(list2.iterator());
        nestedIterators.add(list3.iterator());
    }

    /**
     * Builds the iterator under test: an {@link ObjectGraphIterator} that flattens
     * the nested iterators created in {@link #setUp()}.
     */
    private ObjectGraphIterator<Object> makeObject() {
        return new ObjectGraphIterator<>(nestedIterators.iterator());
    }

    @Test
    void testFullIterator() {
        final Iterator<Object> it = makeObject();

        // A full iterator must report at least one element...
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");
        // ...and yielding that element must not throw.
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Drain the remaining elements.
        while (it.hasNext()) {
            it.next();
        }

        // Once exhausted, next() must signal the end of the iteration.
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
