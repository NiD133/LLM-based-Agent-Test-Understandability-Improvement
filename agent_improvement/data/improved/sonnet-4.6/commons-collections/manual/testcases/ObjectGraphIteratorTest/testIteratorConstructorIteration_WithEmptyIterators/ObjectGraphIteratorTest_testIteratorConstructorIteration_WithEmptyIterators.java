package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ObjectGraphIterator correctly skips empty iterators when iterating
 * over a mix of empty and non-empty iterators.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_WithEmptyIterators {

    /** Expected elements across all non-empty lists, in iteration order. */
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    protected List<String> list1; // contains "One", "Two", "Three"
    protected List<String> list2; // contains "Four"
    protected List<String> list3; // contains "Five", "Six"

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
     * Verifies that empty iterators interspersed among non-empty iterators are
     * silently skipped, and all elements from the non-empty iterators are
     * returned in order.
     *
     * <p>The iterator list is arranged as:
     * [empty, list1, empty, list2, empty, list3, empty]
     * The expected output is the six elements from list1 + list2 + list3.
     */
    @Test
    void testIteratorConstructorIteration_WithEmptyIterators() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list1.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list3.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        // All six elements should be returned in order, empty iterators skipped
        for (int i = 0; i < 6; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }

        // After all elements are consumed, the iterator must be exhausted
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
