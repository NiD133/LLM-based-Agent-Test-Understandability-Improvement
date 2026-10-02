package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObjectGraphIterator#forEachRemaining(java.util.function.Consumer)}.
 *
 * <p>The iterator under test wraps three nested string iterators. Flattening the
 * graph should therefore visit every string from all three lists, in order.</p>
 */
public class ObjectGraphIteratorTest_testForEachRemaining {

    /** Iterators over three small lists, fed to the ObjectGraphIterator. */
    private List<Iterator<String>> nestedIterators;

    /**
     * Builds an {@link ObjectGraphIterator} that flattens the three nested
     * iterators set up in {@link #setUp()}.
     *
     * @return a fresh iterator over all elements of the three lists
     */
    private ObjectGraphIterator<Object> newGraphIterator() {
        return new ObjectGraphIterator<>(nestedIterators.iterator());
    }

    @BeforeEach
    public void setUp() {
        final List<String> list1 = new ArrayList<>();
        list1.add("One");
        list1.add("Two");
        list1.add("Three");

        final List<String> list2 = new ArrayList<>();
        list2.add("Four");

        final List<String> list3 = new ArrayList<>();
        list3.add("Five");
        list3.add("Six");

        nestedIterators = new ArrayList<>();
        nestedIterators.add(list1.iterator());
        nestedIterators.add(list2.iterator());
        nestedIterators.add(list3.iterator());
    }

    /**
     * forEachRemaining should visit exactly the same elements, in the same
     * order, as a straightforward iteration of an equivalent iterator.
     */
    @Test
    void testForEachRemaining() {
        final List<Object> expected = IteratorUtils.toList(newGraphIterator());

        final List<Object> actual = new ArrayList<>();
        newGraphIterator().forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
