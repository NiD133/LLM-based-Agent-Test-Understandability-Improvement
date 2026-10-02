package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testForEachRemaining {

    // Source data: 7 strings used to back the bounded iterator
    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Creates a BoundedIterator that skips the first element (offset=1) and
     * returns at most {@code testList.size() - 1} elements, i.e. everything
     * except the first entry.
     */
    private Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    /**
     * Tests {@link Iterator#forEachRemaining}: verifies that consuming elements
     * via the lambda-based forEachRemaining produces the same sequence as
     * consuming them via explicit iterator calls.
     */
    @Test
    void testForEachRemaining() {
        // Build the expected list by iterating the bounded iterator eagerly
        final List<String> expected = IteratorUtils.toList(makeObject());

        // Build the actual list using forEachRemaining and a method reference
        final Iterator<String> it = makeObject();
        final List<String> actual = new ArrayList<>();
        it.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
