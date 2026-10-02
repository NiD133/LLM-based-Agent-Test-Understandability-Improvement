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

    private final String[] expectedValues = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> firstValues;
    private List<String> secondValues;
    private List<String> thirdValues;

    @BeforeEach
    public void setUp() {
        firstValues = new ArrayList<>();
        firstValues.add("One");
        firstValues.add("Two");
        firstValues.add("Three");

        secondValues = new ArrayList<>();
        secondValues.add("Four");

        thirdValues = new ArrayList<>();
        thirdValues.add("Five");
        thirdValues.add("Six");
    }

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        final List<Iterator<String>> iteratorsWithEmptyIteratorsBetweenValues = new ArrayList<>();
        iteratorsWithEmptyIteratorsBetweenValues.add(IteratorUtils.<String>emptyIterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(firstValues.iterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(IteratorUtils.<String>emptyIterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(secondValues.iterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(IteratorUtils.<String>emptyIterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(thirdValues.iterator());
        iteratorsWithEmptyIteratorsBetweenValues.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> iterator = new ObjectGraphIterator<>(iteratorsWithEmptyIteratorsBetweenValues.iterator(), null);

        for (int index = 0; index < expectedValues.length; index++) {
            assertTrue(iterator.hasNext());
            assertEquals(expectedValues[index], iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
