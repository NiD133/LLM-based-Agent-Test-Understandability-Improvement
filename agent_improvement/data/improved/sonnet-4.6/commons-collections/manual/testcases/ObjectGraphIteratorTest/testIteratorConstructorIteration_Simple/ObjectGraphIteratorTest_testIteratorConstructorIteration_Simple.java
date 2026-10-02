package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ObjectGraphIterator(Iterator) correctly flattens a sequence of
 * iterators into a single ordered stream of elements.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Simple {

    // The six elements spread across three sub-lists, in expected iteration order
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    protected List<String> list1;
    protected List<String> list2;
    protected List<String> list3;
    protected List<Iterator<String>> iteratorList;

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

        // iteratorList holds one iterator per sub-list; the ObjectGraphIterator
        // under test will walk through each in order.
        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    @Test
    void testIteratorConstructorIteration_Simple() {
        // Wrap the iterator-of-iterators; no transformer needed because each
        // element is already a leaf value (String), not a nested iterator.
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        // Verify that all six elements are returned in the correct order
        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext(), "Expected hasNext() true at position " + i);
            assertEquals(testArray[i], it.next(), "Wrong element at position " + i);
        }

        // Verify the iterator signals exhaustion correctly
        assertFalse(it.hasNext(), "Iterator should report no more elements after the last value");
        assertThrows(NoSuchElementException.class, () -> it.next(),
                "Calling next() on an exhausted iterator must throw NoSuchElementException");
    }
}
