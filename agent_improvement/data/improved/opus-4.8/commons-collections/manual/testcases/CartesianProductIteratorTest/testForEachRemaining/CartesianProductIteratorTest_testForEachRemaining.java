package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CartesianProductIterator#forEachRemaining(java.util.function.Consumer)}
 * visits every tuple of the Cartesian product exactly once and in the expected order.
 */
public class CartesianProductIteratorTest_testForEachRemaining {

    /** The three input iterables whose Cartesian product is being tested. */
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
     * forEachRemaining should deliver one tuple per combination of
     * (letter, number, symbol). With 3 letters x 3 numbers x 2 symbols that is
     * 18 tuples, produced in nested-loop order with the last iterable varying fastest.
     */
    @Test
    void testForEachRemaining() {
        final CartesianProductIterator<Character> iterator =
                new CartesianProductIterator<>(letters, numbers, symbols);

        final List<Character[]> actualTuples = new ArrayList<>();
        iterator.forEachRemaining(tuple -> actualTuples.add(tuple.toArray(new Character[0])));

        final int expectedTupleCount = letters.size() * numbers.size() * symbols.size();
        assertEquals(expectedTupleCount, actualTuples.size());

        final Iterator<Character[]> actualTupleIterator = actualTuples.iterator();
        for (final Character letter : letters) {
            for (final Character number : numbers) {
                for (final Character symbol : symbols) {
                    assertArrayEquals(new Character[] { letter, number, symbol },
                            actualTupleIterator.next());
                }
            }
        }
    }
}
