package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CartesianProductIterator#remove()} is unsupported.
 * <p>
 * The iterator does not support element removal, so calling {@code remove()}
 * must throw an {@link UnsupportedOperationException}.
 */
public class CartesianProductIteratorTest_testRemoveThrows {

    @Test
    void testRemoveThrows() {
        final List<Character> letters = Arrays.asList('A', 'B', 'C');
        final List<Character> numbers = Arrays.asList('1', '2', '3');
        final List<Character> symbols = Arrays.asList('!', '?');

        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, numbers, symbols);

        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
