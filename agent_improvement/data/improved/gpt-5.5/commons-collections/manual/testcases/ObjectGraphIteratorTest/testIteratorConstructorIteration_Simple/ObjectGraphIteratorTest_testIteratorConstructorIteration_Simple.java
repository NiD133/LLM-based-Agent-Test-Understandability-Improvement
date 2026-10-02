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

public class ObjectGraphIteratorTest_testIteratorConstructorIteration_Simple {

    private final String[] expectedValues = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> firstGroup;
    private List<String> secondGroup;
    private List<String> thirdGroup;

    @BeforeEach
    public void setUp() {
        firstGroup = new ArrayList<>();
        firstGroup.add("One");
        firstGroup.add("Two");
        firstGroup.add("Three");

        secondGroup = new ArrayList<>();
        secondGroup.add("Four");

        thirdGroup = new ArrayList<>();
        thirdGroup.add("Five");
        thirdGroup.add("Six");
    }

    @Test
    void testIteratorConstructorIteration_Simple() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(firstGroup.iterator());
        iteratorList.add(secondGroup.iterator());
        iteratorList.add(thirdGroup.iterator());

        final Iterator<Object> iterator = new ObjectGraphIterator<>(iteratorList.iterator());

        for (int i = 0; i < expectedValues.length; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(expectedValues[i], iterator.next());
        }

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
