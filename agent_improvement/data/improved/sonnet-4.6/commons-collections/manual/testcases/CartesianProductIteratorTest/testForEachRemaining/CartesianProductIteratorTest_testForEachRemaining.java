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
 * Tests for {@link CartesianProductIterator#forEachRemaining(java.util.function.Consumer)}.
 * <p>
 * The setup creates a 3×3×2 product space: letters {A,B,C} × numbers {1,2,3} × symbols {!,?},
 * yielding 18 tuples in lexicographic order (letters outermost, symbols innermost).
 */
public class CartesianProductIteratorTest_testForEachRemaining {

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> symbols;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
    }

    /** Creates the iterator under test spanning all three input lists. */
    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    /**
     * Verifies that {@code forEachRemaining} delivers every tuple to the consumer
     * in the correct nested-loop order: letters outermost, symbols innermost.
     * Expected tuple count: 3 (letters) × 3 (numbers) × 2 (symbols) = 18.
     */
    @Test
    void testForEachRemaining() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it = makeObject();
        it.forEachRemaining(tuple -> resultsList.add(tuple.toArray(new Character[0])));

        assertEquals(18, resultsList.size());

        final Iterator<Character[]> itResults = resultsList.iterator();
        for (final Character a : letters) {
            for (final Character b : numbers) {
                for (final Character c : symbols) {
                    assertArrayEquals(new Character[] { a, b, c }, itResults.next());
                }
            }
        }
    }
}
