package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObjectGraphIterator#forEachRemaining} by verifying that
 * consuming elements via a Consumer produces the same sequence as
 * collecting elements via {@link Iterator#next()}.
 *
 * <p>The iterator under test traverses three nested lists:
 * ["One","Two","Three"], ["Four"], ["Five","Six"] — six elements in total.
 */
public class ObjectGraphIteratorTest_testForEachRemaining {

    /** Flat expected sequence: the concatenation of all three sublists. */
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    protected List<String> list1;
    protected List<String> list2;
    protected List<String> list3;

    /** An iterator-of-iterators whose flat traversal yields {@link #testArray}. */
    protected List<Iterator<String>> iteratorList;

    /**
     * Builds an {@link ObjectGraphIterator} over an empty collection so that
     * {@link Iterator#hasNext()} returns {@code false} immediately.
     */
    public ObjectGraphIterator<Object> makeEmptyIterator() {
        return new ObjectGraphIterator<>(new ArrayList<>().iterator());
    }

    /**
     * Builds an {@link ObjectGraphIterator} that will yield the six elements
     * defined in {@link #testArray} by traversing {@link #iteratorList}.
     */
    public ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(iteratorList.iterator());
    }

    /**
     * Initialises the three sublists and wraps their iterators in
     * {@link #iteratorList} so that every test starts from a fresh state.
     */
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
     * Verifies that {@link Iterator#forEachRemaining} delivers every element in
     * the correct order.
     *
     * <p>Strategy: collect the expected sequence using a freshly created iterator
     * (via {@link IteratorUtils#toList}), then collect the actual sequence from a
     * second fresh iterator via {@code forEachRemaining}, and assert equality.
     */
    @Test
    void testForEachRemaining() {
        // Collect the expected element order using the conventional next()-based approach.
        final List<Object> expected = IteratorUtils.toList(makeObject());

        // Collect the actual element order using forEachRemaining with a method reference.
        final Iterator<Object> iterator = makeObject();
        final List<Object> actual = new ArrayList<>();
        iterator.forEachRemaining(actual::add);

        assertEquals(expected, actual,
                "forEachRemaining must yield the same elements in the same order as next()");
    }
}
