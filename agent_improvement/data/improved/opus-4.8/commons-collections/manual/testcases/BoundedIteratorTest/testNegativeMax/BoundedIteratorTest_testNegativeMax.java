package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedIterator} rejects a negative {@code max} argument.
 */
public class BoundedIteratorTest_testNegativeMax {

    /** Sample data used to build the iterator under test. */
    private final List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    /**
     * Constructing a {@link BoundedIterator} with a negative {@code max} must fail
     * with an {@link IllegalArgumentException} describing the offending parameter.
     */
    @Test
    void testNegativeMax() {
        final long negativeMax = -1;
        final long offset = 3;

        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(elements.iterator(), offset, negativeMax));

        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }
}
