package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ObjectGraphIterator}, when built from an iterator of iterators,
 * supports {@link Iterator#remove()} by deleting elements from the underlying lists.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorRemove {

    /** The six elements expected, in iteration order, across the three lists. */
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
    void testIteratorConstructorRemove() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        iterators.add(list3.iterator());

        final Iterator<Object> graphIterator = new ObjectGraphIterator<>(iterators.iterator());

        // Walk every element and remove it through the graph iterator.
        for (final String expected : EXPECTED_ELEMENTS) {
            assertEquals(expected, graphIterator.next());
            graphIterator.remove();
        }

        // The iteration is exhausted and every removal reached the backing lists.
        assertFalse(graphIterator.hasNext());
        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
        assertEquals(0, list3.size());
    }
}
