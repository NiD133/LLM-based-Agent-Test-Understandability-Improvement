package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteration_IteratorOfIteratorsWithEmptyIterators {

    private static final String[] EXPECTED_VALUES = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> firstValues;
    private List<String> middleValues;
    private List<String> finalValues;

    @BeforeEach
    public void setUp() {
        firstValues = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        middleValues = new ArrayList<>(Arrays.asList("Four"));
        finalValues = new ArrayList<>(Arrays.asList("Five", "Six"));
    }

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(firstValues.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(middleValues.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(finalValues.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());

        final Iterator<Object> iterator = new ObjectGraphIterator<>(iteratorList.iterator(), null);

        for (int i = 0; i < EXPECTED_VALUES.length; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(EXPECTED_VALUES[i], iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
