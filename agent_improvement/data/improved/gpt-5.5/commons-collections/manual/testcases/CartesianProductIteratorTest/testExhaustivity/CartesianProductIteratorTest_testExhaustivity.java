package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testExhaustivity {

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> symbols;
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    public CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    public boolean supportsRemove() {
        return false;
    }

    /**
     * Tests that all tuples are returned in nested-loop order.
     */
    @Test
    void testExhaustivity() {
        final List<Character[]> actualTuples = new ArrayList<>();
        final CartesianProductIterator<Character> iterator = makeObject();

        while (iterator.hasNext()) {
            final List<Character> tuple = iterator.next();
            actualTuples.add(tuple.toArray(new Character[0]));
        }

        assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals(18, actualTuples.size());
        assertTuplesMatchNestedLoopOrder(actualTuples);
    }

    private void assertTuplesMatchNestedLoopOrder(final List<Character[]> actualTuples) {
        final Iterator<Character[]> tupleIterator = actualTuples.iterator();
        for (final Character letter : letters) {
            for (final Character number : numbers) {
                for (final Character symbol : symbols) {
                    assertArrayEquals(new Character[] { letter, number, symbol }, tupleIterator.next());
                }
            }
        }
    }
}
