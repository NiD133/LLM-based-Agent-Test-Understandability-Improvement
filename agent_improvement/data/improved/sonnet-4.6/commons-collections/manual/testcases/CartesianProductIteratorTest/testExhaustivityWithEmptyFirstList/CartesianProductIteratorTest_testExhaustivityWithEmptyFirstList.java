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

public class CartesianProductIteratorTest_testExhaustivityWithEmptyFirstList {

    private List<Character> numbers;

    private List<Character> symbols;

    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    /**
     * Verifies that the iterator yields no tuples when the first input list is
     * empty (an empty list makes the whole Cartesian product empty), and that
     * calling next() on the exhausted iterator throws NoSuchElementException.
     */
    @Test
    void testExhaustivityWithEmptyFirstList() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(emptyList, numbers, symbols);
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }
        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }
}
