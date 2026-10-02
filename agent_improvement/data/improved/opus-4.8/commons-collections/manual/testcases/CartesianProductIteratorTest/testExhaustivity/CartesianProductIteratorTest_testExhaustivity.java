package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CartesianProductIterator} produces every tuple of the
 * Cartesian product exactly once and in the expected order.
 */
public class CartesianProductIteratorTest_testExhaustivity {

    /** First dimension of the Cartesian product. */
    private List<Character> letters;

    /** Second dimension of the Cartesian product. */
    private List<Character> numbers;

    /** Third dimension of the Cartesian product. */
    private List<Character> symbols;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
    }

    /**
     * Iterating over the product of {@code letters x numbers x symbols} should
     * yield all 3 * 3 * 2 = 18 tuples, ordered as nested for-loops (the last
     * dimension varying fastest), and the iterator should be exhausted
     * afterwards.
     */
    @Test
    void testExhaustivity() {
        final CartesianProductIterator<Character> iterator =
                new CartesianProductIterator<>(letters, numbers, symbols);

        // Collect every tuple the iterator yields.
        final List<Character[]> actualTuples = new ArrayList<>();
        while (iterator.hasNext()) {
            final List<Character> tuple = iterator.next();
            actualTuples.add(tuple.toArray(new Character[0]));
        }

        // Once exhausted, next() must reject any further calls.
        assertThrows(NoSuchElementException.class, iterator::next);

        // 3 letters * 3 numbers * 2 symbols = 18 distinct tuples.
        assertEquals(18, actualTuples.size());

        // The tuples must appear in nested-loop order with symbols varying fastest.
        final Iterator<Character[]> actualTupleIterator = actualTuples.iterator();
        for (final Character letter : letters) {
            for (final Character number : numbers) {
                for (final Character symbol : symbols) {
                    assertArrayEquals(
                            new Character[] { letter, number, symbol },
                            actualTupleIterator.next());
                }
            }
        }
    }
}
