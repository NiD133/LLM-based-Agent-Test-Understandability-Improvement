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
 * Verifies that {@link CartesianProductIterator} yields no tuples when one of
 * the input lists is empty, since the Cartesian product of any set with the
 * empty set is empty.
 */
public class CartesianProductIteratorTest_testExhaustivityWithEmptyList {

    /** A non-empty list used as the first factor of the product. */
    private List<Character> letters;

    /** A non-empty list used as the last factor of the product. */
    private List<Character> symbols;

    /** The empty list that should collapse the whole product to nothing. */
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    /**
     * When any input list is empty the iterator must produce zero tuples,
     * report no further elements, and throw {@link NoSuchElementException}
     * once {@code next()} is called past the end.
     */
    @Test
    void testExhaustivityWithEmptyList() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, emptyList, symbols);

        final List<Character[]> tuples = new ArrayList<>();
        while (it.hasNext()) {
            tuples.add(it.next().toArray(new Character[0]));
        }

        assertEquals(0, tuples.size(), "an empty factor must yield no tuples");
        assertThrows(NoSuchElementException.class, it::next);
    }
}
