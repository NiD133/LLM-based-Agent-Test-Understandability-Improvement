package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CartesianProductIterator#remove()} is an unsupported operation.
 */
public class CartesianProductIteratorTest_testRemove {

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> symbols;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
    }

    /**
     * Creates a Cartesian product iterator over the three sample lists.
     *
     * @return a non-empty iterator
     */
    private CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    /**
     * The iterator does not support removal, so {@code remove()} must throw
     * {@link UnsupportedOperationException}.
     */
    @Test
    void testRemove() {
        final CartesianProductIterator<Character> it = makeObject();

        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
