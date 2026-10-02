package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link CartesianProductIterator} built with an empty iterable
 * yields no tuples, since the Cartesian product with an empty set is empty.
 */
public class CartesianProductIteratorTest_testEmptyCollection {

    @Test
    void testEmptyCollection() {
        final List<Character> letters = Arrays.asList('A', 'B', 'C');

        // Combining a non-empty iterable with an empty one produces an empty product.
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, Collections.emptyList());

        assertFalse(it.hasNext(), "iterator over an empty product should have no elements");
        assertThrows(NoSuchElementException.class, it::next,
                "next() should fail when there are no tuples");
    }
}
