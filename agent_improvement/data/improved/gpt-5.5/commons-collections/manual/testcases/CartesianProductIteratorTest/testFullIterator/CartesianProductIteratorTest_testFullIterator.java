package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testFullIterator {

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
        // Extension hook matching the original iterator-test contract.
    }

    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<List<Character>> iterator = makeObject();

        assertTrue(iterator.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(iterator::next, "Full iterators must have at least one element");

        while (iterator.hasNext()) {
            iterator.next();
            verify();
        }

        assertThrows(
                NoSuchElementException.class,
                iterator::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(iterator.toString());
    }
}
