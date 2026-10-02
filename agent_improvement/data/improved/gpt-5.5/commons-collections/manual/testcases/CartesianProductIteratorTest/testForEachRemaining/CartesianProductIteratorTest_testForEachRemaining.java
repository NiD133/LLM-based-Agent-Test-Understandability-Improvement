package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testForEachRemaining {

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> symbols;
    private List<Character> emptyList;

    public CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    public boolean supportsRemove() {
        return false;
    }

    public boolean supportsEmptyIterator() {
        return true;
    }

    public boolean supportsFullIterator() {
        return true;
    }

    public void verify() {
        // This test class does not need additional cross-verification.
    }

    /**
     * Tests that forEachRemaining provides every tuple in the same order as
     * nested loops over the input iterables.
     */
    @Test
    void testForEachRemaining() {
        final List<Character[]> actualTuples = new ArrayList<>();
        final CartesianProductIterator<Character> iterator = makeObject();

        iterator.forEachRemaining(tuple -> actualTuples.add(tuple.toArray(new Character[0])));

        assertEquals(18, actualTuples.size());
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
