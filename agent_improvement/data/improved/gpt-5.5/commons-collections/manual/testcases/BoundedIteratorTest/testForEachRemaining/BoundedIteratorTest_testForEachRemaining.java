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

    private static final String[] TEST_VALUES = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_VALUES);
    }

    public Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    /**
     * Tests {@link Iterator#forEachRemaining(java.util.function.Consumer)}.
     */
    @Test
    void testForEachRemaining() {
        final List<String> expected = IteratorUtils.toList(makeObject());
        final Iterator<String> iterator = makeObject();
        final List<String> actual = new ArrayList<>();

        iterator.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
