package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ObjectGraphIterator} skips over empty iterators when it
 * walks an "iterator of iterators", yielding every element from the non-empty
 * iterators in order while ignoring the empty ones interleaved between them.
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    /** The elements expected from the iteration, in order. */
    private static final String[] EXPECTED_ELEMENTS = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

    @BeforeEach
    public void setUp() {
        list1 = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        list2 = new ArrayList<>(Arrays.asList("Four"));
        list3 = new ArrayList<>(Arrays.asList("Five", "Six"));
    }

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        // Build an iterator of iterators where empty iterators are interleaved
        // before, between, and after the three non-empty source lists.
        final List<Iterator<String>> iteratorOfIterators = new ArrayList<>();
        iteratorOfIterators.add(IteratorUtils.<String>emptyIterator());
        iteratorOfIterators.add(list1.iterator());
        iteratorOfIterators.add(IteratorUtils.<String>emptyIterator());
        iteratorOfIterators.add(list2.iterator());
        iteratorOfIterators.add(IteratorUtils.<String>emptyIterator());
        iteratorOfIterators.add(list3.iterator());
        iteratorOfIterators.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> graphIterator =
                new ObjectGraphIterator<>(iteratorOfIterators.iterator(), null);

        // The empty iterators must be transparently skipped, so the iteration
        // yields exactly the elements of the non-empty lists, in order.
        for (final String expected : EXPECTED_ELEMENTS) {
            assertTrue(graphIterator.hasNext());
            assertEquals(expected, graphIterator.next());
        }
        assertFalse(graphIterator.hasNext());
    }
}
