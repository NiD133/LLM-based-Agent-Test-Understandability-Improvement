package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link ObjectGraphIterator} built from an iterator-of-iterators
 * silently skips over any empty iterators that are mixed in among the non-empty ones,
 * yielding only the actual elements in order.
 */
public class ObjectGraphIteratorTest_testIteratorConstructorIteration_WithEmptyIterators {

    @Test
    void testIteratorConstructorIteration_WithEmptyIterators() {
        final List<String> first = Arrays.asList("One", "Two", "Three");
        final List<String> second = Arrays.asList("Four");
        final List<String> third = Arrays.asList("Five", "Six");

        // Interleave empty iterators between (and around) the populated ones to
        // confirm the empty iterators are transparently skipped during iteration.
        final List<Iterator<String>> iteratorsWithGaps = Arrays.asList(
                IteratorUtils.<String>emptyIterator(),
                first.iterator(),
                IteratorUtils.<String>emptyIterator(),
                second.iterator(),
                IteratorUtils.<String>emptyIterator(),
                third.iterator(),
                IteratorUtils.<String>emptyIterator());

        final Iterator<Object> graphIterator =
                new ObjectGraphIterator<>(iteratorsWithGaps.iterator());

        // The flattened sequence should be exactly the elements of the non-empty lists, in order.
        final String[] expectedElements = { "One", "Two", "Three", "Four", "Five", "Six" };
        for (final String expected : expectedElements) {
            assertTrue(graphIterator.hasNext());
            assertEquals(expected, graphIterator.next());
        }

        assertFalse(graphIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> graphIterator.next());
    }
}
