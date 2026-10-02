package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructorRemove {

    private static final String[] EXPECTED_VALUES = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> firstList;
    private List<String> secondList;
    private List<String> thirdList;

    @BeforeEach
    public void setUp() {
        firstList = new ArrayList<>();
        firstList.add("One");
        firstList.add("Two");
        firstList.add("Three");

        secondList = new ArrayList<>();
        secondList.add("Four");

        thirdList = new ArrayList<>();
        thirdList.add("Five");
        thirdList.add("Six");
    }

    @Test
    void testIteratorConstructorRemove() {
        final List<Iterator<String>> nestedIterators = new ArrayList<>();
        nestedIterators.add(firstList.iterator());
        nestedIterators.add(secondList.iterator());
        nestedIterators.add(thirdList.iterator());

        final Iterator<Object> iterator = new ObjectGraphIterator<>(nestedIterators.iterator());
        for (int i = 0; i < EXPECTED_VALUES.length; i++) {
            assertEquals(EXPECTED_VALUES[i], iterator.next());
            iterator.remove();
        }

        assertFalse(iterator.hasNext());
        assertEquals(0, firstList.size());
        assertEquals(0, secondList.size());
        assertEquals(0, thirdList.size());
    }
}
