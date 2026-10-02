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
 * Tests that CartesianProductIterator yields every tuple when the same list is
 * supplied as multiple dimensions (i.e. shared-reference inputs are treated
 * independently and fully exhausted).
 */
public class CartesianProductIteratorTest_testExhaustivityWithSameList {

    // The single input list used as all three dimensions in the Cartesian product.
    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    /**
     * Verifies that passing the same list object three times produces all 3^3 = 27
     * ordered tuples in lexicographic order, and that calling next() afterwards
     * throws NoSuchElementException.
     */
    @Test
    void testExhaustivityWithSameList() {
        // Collect every tuple emitted by the iterator.
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, letters, letters);
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }

        // Once exhausted, next() must throw.
        assertThrows(NoSuchElementException.class, it::next);

        // 3 × 3 × 3 = 27 tuples expected.
        assertEquals(27, resultsList.size());

        // Verify every tuple matches the expected lexicographic ordering.
        final Iterator<Character[]> itResults = resultsList.iterator();
        for (final Character a : letters) {
            for (final Character b : letters) {
                for (final Character c : letters) {
                    assertArrayEquals(new Character[] { a, b, c }, itResults.next());
                }
            }
        }
    }
}
