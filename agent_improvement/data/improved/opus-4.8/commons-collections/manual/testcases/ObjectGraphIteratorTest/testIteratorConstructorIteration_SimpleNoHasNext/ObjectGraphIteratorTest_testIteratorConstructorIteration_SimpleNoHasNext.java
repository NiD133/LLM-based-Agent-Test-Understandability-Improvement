package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link ObjectGraphIterator} built from an iterator of iterators
 * flattens the nested iterators and walks every element in order, using only
 * {@code next()} (i.e. without ever calling {@code hasNext()}).
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_SimpleNoHasNext {

    /** The elements expected from the flattened iteration, in order. */
    private static final String[] EXPECTED_ELEMENTS = { "One", "Two", "Three", "Four", "Five", "Six" };

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
    void testIteratorConstructorIteration_SimpleNoHasNext() {
        final List<Iterator<String>> iteratorOfIterators = new ArrayList<>();
        iteratorOfIterators.add(firstList.iterator());
        iteratorOfIterators.add(secondList.iterator());
        iteratorOfIterators.add(thirdList.iterator());

        final Iterator<Object> graphIterator = new ObjectGraphIterator<>(iteratorOfIterators.iterator());

        // Walk every expected element using next() only, never calling hasNext().
        for (final String expected : EXPECTED_ELEMENTS) {
            assertEquals(expected, graphIterator.next());
        }

        // Once all nested iterators are exhausted, next() must fail.
        assertThrows(NoSuchElementException.class, () -> graphIterator.next());
    }
}
