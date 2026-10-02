package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testForEachRemaining {

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

    private ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(nestedIterators.iterator());
    }

    /**
     * Tests {@link Iterator#forEachRemaining(java.util.function.Consumer)}.
     */
    @Test
    void testForEachRemaining() {
        final List<Object> expected = IteratorUtils.toList(makeObject());
        final Iterator<Object> iterator = makeObject();
        final List<Object> actual = new ArrayList<>();

        iterator.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
