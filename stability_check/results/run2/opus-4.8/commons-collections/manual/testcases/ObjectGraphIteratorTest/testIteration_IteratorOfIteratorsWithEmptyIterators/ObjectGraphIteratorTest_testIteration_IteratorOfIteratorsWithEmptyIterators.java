package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ObjectGraphIterator} flattens an iterator-of-iterators
 * correctly even when empty iterators are interleaved between the populated ones.
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    /** The elements expected in iteration order after flattening. */
    private final String[] expectedElements = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

    @BeforeEach
    public void setUp() {
        list1 = new ArrayList<>();
        list1.add("One");
        list1.add("Two");
        list1.add("Three");

        list2 = new ArrayList<>();
        list2.add("Four");

        list3 = new ArrayList<>();
        list3.add("Five");
        list3.add("Six");
    }

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        // Build an iterator of iterators where empty iterators surround each populated one.
        final List<Iterator<String>> nestedIterators = new ArrayList<>();
        nestedIterators.add(IteratorUtils.<String>emptyIterator());
        nestedIterators.add(list1.iterator());
        nestedIterators.add(IteratorUtils.<String>emptyIterator());
        nestedIterators.add(list2.iterator());
        nestedIterators.add(IteratorUtils.<String>emptyIterator());
        nestedIterators.add(list3.iterator());
        nestedIterators.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> graphIterator =
                new ObjectGraphIterator<>(nestedIterators.iterator(), null);

        // The empty iterators should be skipped, yielding the six elements in order.
        for (final String expected : expectedElements) {
            assertTrue(graphIterator.hasNext());
            assertEquals(expected, graphIterator.next());
        }
        assertFalse(graphIterator.hasNext());
    }
}
