package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testFullIterator {

    private List<String> firstIteratorValues;
    private List<String> secondIteratorValues;
    private List<String> thirdIteratorValues;
    private List<Iterator<String>> nestedIterators;

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

        nestedIterators = new ArrayList<>();
        nestedIterators.add(firstIteratorValues.iterator());
        nestedIterators.add(secondIteratorValues.iterator());
        nestedIterators.add(thirdIteratorValues.iterator());
    }

    public ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(nestedIterators.iterator());
    }

    public boolean supportsFullIterator() {
        return true;
    }

    public void verify() {
        // Hook retained from the original iterator test contract.
    }

    /**
     * Test normal iteration behavior.
     */
    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<Object> iterator = makeObject();

        assertTrue(iterator.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(iterator::next, "Full iterators must have at least one element");

        while (iterator.hasNext()) {
            iterator.next();
            verify();
        }

        assertThrows(
            NoSuchElementException.class,
            iterator::next,
            "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(iterator.toString());
    }
}
