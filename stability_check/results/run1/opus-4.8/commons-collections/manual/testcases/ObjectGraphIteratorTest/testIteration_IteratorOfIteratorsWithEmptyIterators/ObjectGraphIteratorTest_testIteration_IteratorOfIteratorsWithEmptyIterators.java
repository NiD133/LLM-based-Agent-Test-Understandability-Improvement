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
 * Tests that an {@link ObjectGraphIterator} built from an iterator of iterators
 * transparently skips over interleaved empty iterators and still visits every
 * element of the non-empty ones, in order.
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    /** The concatenation of list1, list2 and list3, in iteration order. */
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
        // Interleave empty iterators before, between and after the populated ones.
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list1.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list3.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator(), null);

        // The empty iterators must be silently skipped, yielding only the real elements.
        for (final String expected : EXPECTED_ELEMENTS) {
            assertTrue(it.hasNext());
            assertEquals(expected, it.next());
        }
        assertFalse(it.hasNext());
    }
}
