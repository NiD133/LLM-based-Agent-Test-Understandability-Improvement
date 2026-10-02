package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testRemove {

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
        // no additional verification required for this iterator
    }

    /**
     * The Cartesian product iterator does not support removal, so the inherited
     * remove contract test exits after checking that remove() is rejected.
     */
    @Test
    void testRemove() {
        final Iterator<List<Character>> it = makeObject();
        if (!supportsRemove()) {
            assertThrows(UnsupportedOperationException.class, it::remove);
            return;
        }
        assertThrows(IllegalStateException.class, it::remove);
        verify();
        it.next();
        it.remove();
        assertThrows(IllegalStateException.class, it::remove);
    }
}
