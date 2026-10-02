package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a CartesianProductIterator whose inputs include an empty collection
 * produces no elements, because the Cartesian product of any set with the empty
 * set is itself empty.
 */
public class CartesianProductIteratorTest_testEmptyCollection {

    /** Non-empty collection used as one input to the Cartesian product. */
    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    /**
     * When one of the input collections is empty, the iterator must immediately
     * report no elements and throw {@link NoSuchElementException} on {@code next()}.
     */
    @Test
    void testEmptyCollection() {
        // Pairing a non-empty collection with an empty one yields an empty product.
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, Collections.emptyList());

        assertFalse(it.hasNext(),
                "Cartesian product with an empty input collection should have no elements");
        assertThrows(NoSuchElementException.class, it::next,
                "next() on an empty iterator should throw NoSuchElementException");
    }
}
