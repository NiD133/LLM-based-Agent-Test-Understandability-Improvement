package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testFullIterator {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    public Iterator<String> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<String>emptyList().iterator(), 0, 10);
    }

    public Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    public boolean supportsFullIterator() {
        return true;
    }

    public void verify() {
        // Hook retained from the original test contract.
    }

    /**
     * Tests normal iteration behavior.
     */
    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<String> iterator = makeObject();

        assertTrue(iterator.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(iterator::next, "Full iterators must have at least one element");

        while (iterator.hasNext()) {
            iterator.next();
            verify();
        }

        assertThrows(NoSuchElementException.class, iterator::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(iterator.toString());
    }
}
