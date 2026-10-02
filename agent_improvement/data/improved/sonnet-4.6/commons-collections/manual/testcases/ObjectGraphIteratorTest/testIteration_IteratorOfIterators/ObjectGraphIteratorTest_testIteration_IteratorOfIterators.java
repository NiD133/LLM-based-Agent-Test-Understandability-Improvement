package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ObjectGraphIterator} can flatten an iterator-of-iterators
 * into a single sequence without a transformer.
 */
public class ObjectGraphIteratorTest_testIteration_IteratorOfIterators {

    // Expected traversal order across all three sub-lists
    private final String[] expectedElements = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

    // Outer iterator whose elements are themselves iterators over the sub-lists
    private List<Iterator<String>> outerIteratorList;

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

        outerIteratorList = new ArrayList<>();
        outerIteratorList.add(list1.iterator());
        outerIteratorList.add(list2.iterator());
        outerIteratorList.add(list3.iterator());
    }

    /**
     * Verifies that ObjectGraphIterator with a null transformer flattens an
     * iterator of iterators, yielding each leaf element in encounter order.
     */
    @Test
    void testIteration_IteratorOfIterators() {
        // null transformer: ObjectGraphIterator unwraps nested iterators automatically
        final Iterator<Object> flatIterator =
                new ObjectGraphIterator<>(outerIteratorList.iterator(), null);

        for (int i = 0; i < expectedElements.length; i++) {
            assertTrue(flatIterator.hasNext(),
                    "Expected more elements before index " + i);
            assertEquals(expectedElements[i], flatIterator.next(),
                    "Element at index " + i + " did not match");
        }

        assertFalse(flatIterator.hasNext(),
                "Iterator should be exhausted after all elements are consumed");
    }
}
