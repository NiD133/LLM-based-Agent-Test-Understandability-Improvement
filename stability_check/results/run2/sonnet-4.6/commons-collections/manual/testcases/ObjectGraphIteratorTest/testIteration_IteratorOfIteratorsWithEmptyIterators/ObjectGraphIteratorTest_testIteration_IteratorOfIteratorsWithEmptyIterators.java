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
 * Tests that ObjectGraphIterator transparently skips empty iterators when
 * iterating over a sequence of iterators (some of which may be empty).
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    // The six leaf values spread across three non-empty sub-lists
    protected final String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    protected List<String> list1; // ["One", "Two", "Three"]
    protected List<String> list2; // ["Four"]
    protected List<String> list3; // ["Five", "Six"]

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

    /**
     * Verifies that ObjectGraphIterator yields all leaf elements in order even when
     * empty iterators are interspersed before, between, and after the non-empty ones.
     *
     * Structure of the outer iterator:
     *   [empty, list1-iter, empty, list2-iter, empty, list3-iter, empty]
     *
     * Expected traversal: "One", "Two", "Three", "Four", "Five", "Six"
     */
    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        // Build an outer iterator that sandwiches each real sub-iterator between empty ones
        final List<Iterator<String>> outerList = new ArrayList<>();
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list1.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list2.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list3.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());

        // null transformer means each element from a sub-iterator is returned as-is
        final Iterator<Object> it = new ObjectGraphIterator<>(outerList.iterator(), null);

        // All six leaf values must be returned in order, with no gaps or duplicates
        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext(), "Expected more elements at index " + i);
            assertEquals(testArray[i], it.next(), "Unexpected value at index " + i);
        }

        // The iterator must be exhausted after all elements have been consumed
        assertFalse(it.hasNext(), "Iterator should be exhausted after all elements");
    }
}
