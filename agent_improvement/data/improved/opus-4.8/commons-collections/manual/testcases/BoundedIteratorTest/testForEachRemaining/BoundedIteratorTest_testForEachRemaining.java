package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedIterator#forEachRemaining(java.util.function.Consumer)}.
 */
public class BoundedIteratorTest_testForEachRemaining {

    /** Source elements decorated by the iterator under test. */
    private static final String[] SOURCE_ELEMENTS = { "a", "b", "c", "d", "e", "f", "g" };

    /** Skip the first element, then return all the rest. */
    private static final int OFFSET = 1;

    private List<String> sourceList;

    @BeforeEach
    public void setUp() {
        sourceList = Arrays.asList(SOURCE_ELEMENTS);
    }

    /**
     * Creates a {@link BoundedIterator} over {@link #sourceList} that skips the
     * first element ({@link #OFFSET}) and returns every remaining element.
     */
    private Iterator<String> makeBoundedIterator() {
        final int max = sourceList.size() - OFFSET;
        return new BoundedIterator<>(new ArrayList<>(sourceList).iterator(), OFFSET, max);
    }

    /**
     * {@code forEachRemaining} must visit exactly the bounded elements, in order
     * (i.e. all source elements after the offset: "b" through "g").
     */
    @Test
    void testForEachRemaining() {
        final List<String> expected = IteratorUtils.toList(makeBoundedIterator());

        final List<String> visited = new ArrayList<>();
        makeBoundedIterator().forEachRemaining(visited::add);

        assertEquals(expected, visited);
    }
}
