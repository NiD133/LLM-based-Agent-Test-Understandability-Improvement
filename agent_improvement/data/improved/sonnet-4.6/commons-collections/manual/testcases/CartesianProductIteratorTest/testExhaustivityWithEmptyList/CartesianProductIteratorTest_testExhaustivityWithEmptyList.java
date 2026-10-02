package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that the Cartesian product is empty when any input list is empty.
 *
 * <p>The mathematical property: A × ∅ × B = ∅ for any sets A and B.
 * When one dimension has no elements, no complete tuple can be formed.
 */
public class CartesianProductIteratorTest_testExhaustivityWithEmptyList {

    private List<Character> letters;
    private List<Character> symbols;
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters   = Arrays.asList('A', 'B', 'C');
        symbols   = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    /**
     * Verifies that no tuples are produced when one of the input lists is empty,
     * and that calling {@code next()} on the exhausted iterator throws
     * {@link NoSuchElementException}.
     */
    @Test
    void testExhaustivityWithEmptyList() {
        // An empty list in the middle position should yield zero tuples.
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, emptyList, symbols);

        final List<Character[]> resultsList = new ArrayList<>();
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }

        // After full iteration, next() must throw NoSuchElementException.
        assertThrows(NoSuchElementException.class, it::next);

        // No tuples should have been produced.
        assertEquals(0, resultsList.size());
    }
}
