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

    private static final int EXPECTED_TUPLE_COUNT = 27;

    private List<Character> letters;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
    }

    /**
     * Checks that passing the same list three times still exhausts the full
     * Cartesian product in nested-loop order.
     */
    @Test
    void testExhaustivityWithSameList() {
        final List<Character[]> actualTuples = new ArrayList<>();
        final CartesianProductIterator<Character> iterator = new CartesianProductIterator<>(letters, letters, letters);

        while (iterator.hasNext()) {
            final List<Character> tuple = iterator.next();
            actualTuples.add(tuple.toArray(new Character[0]));
        }

        assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals(EXPECTED_TUPLE_COUNT, actualTuples.size());

        final Iterator<Character[]> actualTupleIterator = actualTuples.iterator();
        for (final Character first : letters) {
            for (final Character second : letters) {
                for (final Character third : letters) {
                    assertArrayEquals(new Character[] { first, second, third }, actualTupleIterator.next());
                }
            }
        }
    }
}
