package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructorRemove {

    // Elements spread across three backing lists to verify cross-list removal
    protected final String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

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
     * Verifies that remove() delegates correctly to the underlying per-list iterator
     * when ObjectGraphIterator is constructed from an iterator-of-iterators.
     * After iterating and removing every element, all three backing lists must be empty.
     */
    @Test
    void testIteratorConstructorRemove() {
        // Build a fresh iterator-of-iterators over the three lists
        List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());

        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        // Consume every element and immediately remove it from its backing list
        for (int i = 0; i < testArray.length; i++) {
            assertEquals(testArray[i], it.next(), "Unexpected element at position " + i);
            it.remove();
        }

        assertFalse(it.hasNext(), "Iterator should be exhausted after consuming all elements");

        // Each backing list must now be empty because remove() was called for every element
        assertEquals(0, list1.size(), "list1 should be empty after removing all its elements");
        assertEquals(0, list2.size(), "list2 should be empty after removing all its elements");
        assertEquals(0, list3.size(), "list3 should be empty after removing all its elements");
    }
}
