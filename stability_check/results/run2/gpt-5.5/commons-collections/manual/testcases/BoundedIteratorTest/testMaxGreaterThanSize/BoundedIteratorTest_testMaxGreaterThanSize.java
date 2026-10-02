package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testMaxGreaterThanSize {

    private static final List<String> SOURCE_VALUES =
            Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    private static final long OFFSET_TO_SKIP_FIRST_ELEMENT = 1;
    private static final long MAX_GREATER_THAN_REMAINING_SIZE = 10;

    /**
     * When the requested maximum is larger than the remaining source iterator,
     * the bounded iterator should stop at the source iterator's final element.
     */
    @Test
    void testMaxGreaterThanSize() {
        final Iterator<String> iterator = new BoundedIterator<>(
                SOURCE_VALUES.iterator(),
                OFFSET_TO_SKIP_FIRST_ELEMENT,
                MAX_GREATER_THAN_REMAINING_SIZE);

        assertRemainingElements(iterator, "b", "c", "d", "e", "f", "g");
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    private void assertRemainingElements(final Iterator<String> iterator, final String... expectedElements) {
        for (final String expectedElement : expectedElements) {
            assertTrue(iterator.hasNext());
            assertEquals(expectedElement, iterator.next());
        }
    }
}
