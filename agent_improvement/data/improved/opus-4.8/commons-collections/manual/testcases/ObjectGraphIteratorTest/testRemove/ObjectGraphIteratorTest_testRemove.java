package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Iterator#remove()} contract of {@link ObjectGraphIterator}.
 */
public class ObjectGraphIteratorTest_testRemove {

    /** Three nested string iterators that the graph iterator flattens to One..Six. */
    private List<Iterator<String>> iteratorList;

    /**
     * Creates an {@link ObjectGraphIterator} that walks the nested iterators,
     * yielding "One", "Two", "Three", "Four", "Five", "Six" in order.
     */
    private ObjectGraphIterator<Object> makeObject() {
        return new ObjectGraphIterator<>(iteratorList.iterator());
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

        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    @Test
    void testRemove() {
        final Iterator<Object> it = makeObject();

        // remove() before any next() call is illegal
        assertThrows(IllegalStateException.class, it::remove);

        // remove() after next() removes the last returned element successfully
        it.next();
        it.remove();

        // a second remove() without an intervening next() is illegal
        assertThrows(IllegalStateException.class, it::remove);
    }
}
