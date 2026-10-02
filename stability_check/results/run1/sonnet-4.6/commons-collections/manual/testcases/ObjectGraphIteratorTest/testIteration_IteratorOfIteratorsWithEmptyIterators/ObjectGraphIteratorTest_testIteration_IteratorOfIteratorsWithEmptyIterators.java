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

    private static final String[] EXPECTED_ELEMENTS = {"One", "Two", "Three", "Four", "Five", "Six"};

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

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

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        // Non-empty iterators are interleaved with empty ones to verify they are skipped transparently
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list1.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list3.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator(), null);

        for (int i = 0; i < EXPECTED_ELEMENTS.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(EXPECTED_ELEMENTS[i], it.next());
        }
        assertFalse(it.hasNext());
    }
}
