package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the normal forward-iteration behaviour of {@link CartesianProductIterator}.
 */
public class CartesianProductIteratorTest_testFullIterator {

    /** The input iterables whose Cartesian product is iterated over. */
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
     * Creates an iterator over the full Cartesian product of the three
     * non-empty input iterables (3 x 3 x 2 = 18 tuples).
     */
    private CartesianProductIterator<Character> makeFullIterator() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    /**
     * A full iterator should yield at least one element, allow iteration to
     * completion, and then throw {@link NoSuchElementException} once exhausted.
     */
    @Test
    void testFullIterator() {
        final Iterator<List<Character>> it = makeFullIterator();

        // The product is non-empty, so iteration must start with an element.
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Drain the rest of the tuples.
        while (it.hasNext()) {
            it.next();
        }

        // Once exhausted, next() must signal the end of iteration.
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
