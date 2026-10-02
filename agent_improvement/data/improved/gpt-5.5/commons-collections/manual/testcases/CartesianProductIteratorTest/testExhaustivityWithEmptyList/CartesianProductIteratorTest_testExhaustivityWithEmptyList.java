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

public class CartesianProductIteratorTest_testExhaustivityWithEmptyList {

    private List<Character> letters;
    private List<Character> symbols;
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    /**
     * No tuples are returned when any input iterable in the Cartesian product is empty.
     */
    @Test
    void testExhaustivityWithEmptyList() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, emptyList, symbols);

        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }
}
