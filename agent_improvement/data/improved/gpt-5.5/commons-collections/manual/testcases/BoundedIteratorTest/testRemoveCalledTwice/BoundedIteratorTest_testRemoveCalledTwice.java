package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveCalledTwice {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Tests {@link BoundedIterator#remove()} when it is called twice without
     * calling {@link BoundedIterator#next()} between the two remove calls.
     */
    @Test
    void testRemoveCalledTwice() {
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());

        iter.remove();

        assertThrows(IllegalStateException.class, () -> iter.remove());
    }
}
