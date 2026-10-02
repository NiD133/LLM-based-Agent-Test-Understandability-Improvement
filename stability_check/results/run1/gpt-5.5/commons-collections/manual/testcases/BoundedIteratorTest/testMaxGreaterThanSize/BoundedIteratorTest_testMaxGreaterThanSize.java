package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testMaxGreaterThanSize {

    private static final long START_AT_SECOND_ELEMENT = 1L;
    private static final long MAX_GREATER_THAN_REMAINING_SIZE = 10L;

    private List<String> testList;

    @BeforeEach
    void setUp() {
        testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    }

    /**
     * When max is larger than the remaining decorated iterator size, the
     * BoundedIterator returns every element from the offset through the end.
     */
    @Test
    void testMaxGreaterThanSize() {
        final Iterator<String> iterator = new BoundedIterator<>(
                testList.iterator(),
                START_AT_SECOND_ELEMENT,
                MAX_GREATER_THAN_REMAINING_SIZE);

        assertNextElement(iterator, "b");
        assertNextElement(iterator, "c");
        assertNextElement(iterator, "d");
        assertNextElement(iterator, "e");
        assertNextElement(iterator, "f");
        assertNextElement(iterator, "g");
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    private static void assertNextElement(final Iterator<String> iterator, final String expectedElement) {
        assertTrue(iterator.hasNext());
        assertEquals(expectedElement, iterator.next());
    }
}
