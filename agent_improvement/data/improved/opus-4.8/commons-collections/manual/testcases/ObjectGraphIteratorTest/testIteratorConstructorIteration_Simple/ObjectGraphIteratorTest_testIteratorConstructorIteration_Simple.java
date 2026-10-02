package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Tests that {@link ObjectGraphIterator}, when built from an iterator of
 * iterators, visits every element of every nested iterator in order.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Simple {

    /** The concatenation of all nested lists, in iteration order. */
    private static final String[] EXPECTED_ELEMENTS = { "One", "Two", "Three", "Four", "Five", "Six" };

    /** The nested lists whose iterators are chained together by the graph iterator. */
    private List<List<String>> nestedLists;

    @BeforeEach
    public void setUp() {
        nestedLists = new ArrayList<>();
        nestedLists.add(Arrays.asList("One", "Two", "Three"));
        nestedLists.add(Arrays.asList("Four"));
        nestedLists.add(Arrays.asList("Five", "Six"));
    }

    @Test
    void testIteratorConstructorIteration_Simple() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        for (final List<String> nestedList : nestedLists) {
            iteratorList.add(nestedList.iterator());
        }

        final Iterator<Object> graphIterator = new ObjectGraphIterator<>(iteratorList.iterator());

        // Every element of every nested iterator is returned in order.
        for (final String expected : EXPECTED_ELEMENTS) {
            assertTrue(graphIterator.hasNext());
            assertEquals(expected, graphIterator.next());
        }

        // Once exhausted, the iterator reports no more elements and rejects next().
        assertFalse(graphIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> graphIterator.next());
    }
}
