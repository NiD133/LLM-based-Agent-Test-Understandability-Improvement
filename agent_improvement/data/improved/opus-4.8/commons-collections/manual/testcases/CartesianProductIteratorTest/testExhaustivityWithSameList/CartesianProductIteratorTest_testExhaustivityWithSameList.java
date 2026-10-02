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

public class CartesianProductIteratorTest_testExhaustivityWithSameList {

    /** The single list used for all three dimensions of the Cartesian product. */
    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    /**
     * When the same list is passed multiple times, the iterator must still emit
     * every tuple of the Cartesian product, in nested-for-loop order.
     */
    @Test
    void testExhaustivityWithSameList() {
        // Build the Cartesian product of letters x letters x letters.
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, letters, letters);

        // Collect every emitted tuple.
        final List<Character[]> resultsList = new ArrayList<>();
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }

        // The iterator is now exhausted; asking for more must fail.
        assertThrows(NoSuchElementException.class, it::next);

        // 3 letters in 3 positions => 3^3 = 27 tuples.
        assertEquals(27, resultsList.size());

        // The tuples must appear in the order produced by nested loops
        // over the three positions (last position varying fastest).
        final Iterator<Character[]> itResults = resultsList.iterator();
        for (final Character first : letters) {
            for (final Character second : letters) {
                for (final Character third : letters) {
                    assertArrayEquals(
                            new Character[] { first, second, third },
                            itResults.next());
                }
            }
        }
    }
}
