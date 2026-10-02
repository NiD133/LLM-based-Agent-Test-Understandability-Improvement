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

    private static final String[] EXPECTED_VALUES = { "One", "Two", "Three", "Four", "Five", "Six" };

    private List<String> firstIteratorValues;
    private List<String> secondIteratorValues;
    private List<String> thirdIteratorValues;

    @BeforeEach
    public void setUp() {
        firstIteratorValues = new ArrayList<>();
        firstIteratorValues.add("One");
        firstIteratorValues.add("Two");
        firstIteratorValues.add("Three");

        secondIteratorValues = new ArrayList<>();
        secondIteratorValues.add("Four");

        thirdIteratorValues = new ArrayList<>();
        thirdIteratorValues.add("Five");
        thirdIteratorValues.add("Six");
    }

    @Test
    void testIteratorConstructorIteration_SimpleNoHasNext() {
        final Iterator<Object> iterator = new ObjectGraphIterator<>(iteratorOfIterators());

        for (int i = 0; i < EXPECTED_VALUES.length; i++) {
            assertEquals(EXPECTED_VALUES[i], iterator.next());
        }
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    private Iterator<Iterator<String>> iteratorOfIterators() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(firstIteratorValues.iterator());
        iterators.add(secondIteratorValues.iterator());
        iterators.add(thirdIteratorValues.iterator());
        return iterators.iterator();
    }
}
