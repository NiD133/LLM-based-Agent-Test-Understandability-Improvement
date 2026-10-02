package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link ObjectGraphIterator} created from an iterator of
 * iterators flattens the nested iterators, yielding every element of every
 * inner iterator in order.
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIterators {

    /** The elements expected from the flattened iteration, in order. */
    private static final String[] EXPECTED_ELEMENTS =
            { "One", "Two", "Three", "Four", "Five", "Six" };

    /** Inner lists whose iterators are nested inside the graph iterator. */
    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

    @BeforeEach
    public void setUp() {
        list1 = Arrays.asList("One", "Two", "Three");
        list2 = Arrays.asList("Four");
        list3 = Arrays.asList("Five", "Six");
    }

    @Test
    void testIteration_IteratorOfIterators() {
        // An iterator over three inner iterators; together they hold all six elements.
        final List<Iterator<String>> nestedIterators = new ArrayList<>();
        nestedIterators.add(list1.iterator());
        nestedIterators.add(list2.iterator());
        nestedIterators.add(list3.iterator());

        // A null transformer means each inner iterator is descended into directly.
        final Iterator<Object> graphIterator =
                new ObjectGraphIterator<>(nestedIterators.iterator(), null);

        // The graph iterator should flatten the three inner iterators into one sequence.
        for (final String expected : EXPECTED_ELEMENTS) {
            assertTrue(graphIterator.hasNext());
            assertEquals(expected, graphIterator.next());
        }
        assertFalse(graphIterator.hasNext());
    }
}
