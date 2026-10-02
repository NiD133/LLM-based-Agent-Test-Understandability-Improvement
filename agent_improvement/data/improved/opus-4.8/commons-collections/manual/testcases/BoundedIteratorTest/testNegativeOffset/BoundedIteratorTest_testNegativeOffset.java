package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BoundedIterator} rejects a negative {@code offset}
 * passed to its constructor.
 */
public class BoundedIteratorTest_testNegativeOffset {

    /** Sample data the iterator is built from. */
    private final List<String> testList = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * Constructing a {@link BoundedIterator} with a negative offset must throw
     * an {@link IllegalArgumentException} carrying a descriptive message.
     */
    @Test
    void testNegativeOffset() {
        final int negativeOffset = -1;
        final int max = 4;

        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), negativeOffset, max));

        assertEquals("Offset parameter must not be negative.", thrown.getMessage());
    }
}
