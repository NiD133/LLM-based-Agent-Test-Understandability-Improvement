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

public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    // Expected elements in iteration order
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    // Three non-empty source lists whose iterators will be interleaved with empty iterators
    protected List<String> list1;
    protected List<String> list2;
    protected List<String> list3;

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
     * Verifies that ObjectGraphIterator correctly skips empty iterators and yields
     * all elements from non-empty iterators in order when empty iterators are placed
     * before, between, and after the non-empty ones.
     *
     * Structure of the outer iterator:
     *   [empty] -> list1 -> [empty] -> list2 -> [empty] -> list3 -> [empty]
     *
     * Expected traversal order: One, Two, Three, Four, Five, Six
     */
    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        final List<Iterator<String>> outerList = new ArrayList<>();
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list1.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list2.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());
        outerList.add(list3.iterator());
        outerList.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> it = new ObjectGraphIterator<>(outerList.iterator(), null);

        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext(), "Expected hasNext() to be true before element index " + i);
            assertEquals(testArray[i], it.next(), "Unexpected element at index " + i);
        }
        assertFalse(it.hasNext(), "Expected no more elements after all six have been consumed");
    }
}
