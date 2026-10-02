package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link CartesianProductIterator} built from at least one empty
 * input iterable yields no tuples at all.
 */
public class CartesianProductIteratorTest_testEmptyCollection {

    @Test
    void testEmptyCollection() {
        final List<Character> letters = Arrays.asList('A', 'B', 'C');

        // Pairing a non-empty iterable with an empty one produces an empty Cartesian product.
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, Collections.emptyList());

        assertFalse(it.hasNext(), "an empty Cartesian product should have no tuples");
        assertThrows(NoSuchElementException.class, it::next,
                "next() must fail when there are no tuples to return");
    }
}
