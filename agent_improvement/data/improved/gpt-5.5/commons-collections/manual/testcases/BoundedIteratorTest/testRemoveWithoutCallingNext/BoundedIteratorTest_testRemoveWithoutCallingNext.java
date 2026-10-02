package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveWithoutCallingNext {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };
    private static final String REMOVE_BEFORE_NEXT_MESSAGE = "remove() cannot be called before calling next()";

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Calling remove() before the bounded iterator has returned an element should
     * still fail, even though construction skips the configured offset.
     */
    @Test
    void testRemoveWithoutCallingNext() {
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        final IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> iter.remove());

        assertEquals(REMOVE_BEFORE_NEXT_MESSAGE, thrown.getMessage());
    }
}
