package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteratorConstructorIteration_SimpleNoHasNext {

    private static final String[] EXPECTED_ELEMENTS = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;
    private List<Iterator<String>> iteratorList;

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

        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    /**
     * Verifies that ObjectGraphIterator traverses nested iterators in order
     * when next() is called directly without hasNext() checks, and throws
     * NoSuchElementException once all elements are exhausted.
     */
    @Test
    void testIteratorConstructorIteration_SimpleNoHasNext() {
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        for (int i = 0; i < EXPECTED_ELEMENTS.length; i++) {
            assertEquals(EXPECTED_ELEMENTS[i], it.next());
        }

        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
