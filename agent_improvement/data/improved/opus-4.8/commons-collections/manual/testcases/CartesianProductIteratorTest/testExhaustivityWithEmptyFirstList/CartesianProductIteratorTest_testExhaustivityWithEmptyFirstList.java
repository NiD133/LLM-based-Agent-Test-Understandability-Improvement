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
 * Verifies that {@link CartesianProductIterator} produces no tuples when the
 * first input list is empty, since the Cartesian product of an empty set is
 * itself empty.
 */
public class CartesianProductIteratorTest_testExhaustivityWithEmptyFirstList {

    /** An empty list used as the first (leading) factor of the product. */
    private List<Character> emptyList;

    /** The second factor of the product. */
    private List<Character> numbers;

    /** The third factor of the product. */
    private List<Character> symbols;

    @BeforeEach
    public void setUp() {
        emptyList = Collections.emptyList();
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
    }

    /**
     * When the first list is empty, the iterator must yield zero tuples and
     * calling {@code next()} must throw {@link NoSuchElementException}.
     */
    @Test
    void testExhaustivityWithEmptyFirstList() {
        final CartesianProductIterator<Character> iterator =
                new CartesianProductIterator<>(emptyList, numbers, symbols);

        final List<List<Character>> producedTuples = new ArrayList<>();
        while (iterator.hasNext()) {
            producedTuples.add(iterator.next());
        }

        assertEquals(0, producedTuples.size(), "no tuples expected for an empty first list");
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
