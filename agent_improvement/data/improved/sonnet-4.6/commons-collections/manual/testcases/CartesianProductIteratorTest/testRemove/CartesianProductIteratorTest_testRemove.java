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

    /**
     * Verifies that {@code CartesianProductIterator.remove()} always throws
     * {@link UnsupportedOperationException}, because this iterator does not
     * support element removal.
     */
    @Test
    void testRemove() {
        final Iterator<List<Character>> iterator = makeObject();

        if (!supportsRemove()) {
            // CartesianProductIterator does not support remove(); calling it must throw
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            return;
        }

        // The following branches are only reached when the iterator supports remove().
        // They are retained here to preserve the full test contract inherited from
        // the abstract base class, even though supportsRemove() currently returns false.

        // remove() before any call to next() must throw IllegalStateException
        assertThrows(IllegalStateException.class, iterator::remove);

        // remove() after next() must succeed without exception
        iterator.next();
        iterator.remove();

        // a second consecutive remove() without an intervening next() must throw
        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
