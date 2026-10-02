package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testRemoveThrows {

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

    @Test
    void testRemoveThrows() {
        final CartesianProductIterator<Character> it = makeObject();

        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
