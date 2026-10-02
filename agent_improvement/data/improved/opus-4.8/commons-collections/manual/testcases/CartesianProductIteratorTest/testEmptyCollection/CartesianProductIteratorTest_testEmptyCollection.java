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
 * Tests that a {@link CartesianProductIterator} built from one non-empty and one
 * empty input produces no tuples, because the Cartesian product with an empty
 * factor is itself empty.
 */
public class CartesianProductIteratorTest_testEmptyCollection {

    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    @Test
    void testEmptyCollection() {
        final CartesianProductIterator<Character> iterator =
                new CartesianProductIterator<>(letters, Collections.emptyList());

        // An empty factor makes the whole product empty, so there is nothing to iterate.
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
