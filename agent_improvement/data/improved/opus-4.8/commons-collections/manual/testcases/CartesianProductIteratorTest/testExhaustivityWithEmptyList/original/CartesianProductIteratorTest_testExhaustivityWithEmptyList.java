package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.collections4.AbstractObjectTest;
import org.apache.commons.collections4.IteratorUtils;

public class CartesianProductIteratorTest_testExhaustivityWithEmptyList {

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
     * Implement this method to return an iterator over an empty collection.
     *
     * @return an empty iterator
     */
    private Iterator<E> __super_makeEmptyIterator();

    /**
     * Implements the abstract superclass method to return the full iterator.
     *
     * @return a full iterator
     */
    private Iterator<E> __super_makeObject();

    /**
     * Tests whether or not we are testing an iterator that can be empty.
     * Default is true.
     *
     * @return true if Iterator can be empty
     */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /**
     * Tests whether or not we are testing an iterator that can contain elements.
     * Default is true.
     *
     * @return true if Iterator can be full
     */
    public boolean supportsFullIterator() {
        return true;
    }

    /**
     * Tests whether or not we are testing an iterator that supports remove().
     * Default is true.
     *
     * @return true if Iterator supports remove
     */
    private boolean __super_supportsRemove() {
        return true;
    }

    /**
     * Tests {@link Iterator#forEachRemaining(java.util.function.Consumer)}.
     */
    private void __super_testForEachRemaining() {
        final List<E> expected = IteratorUtils.toList(makeObject());
        final Iterator<E> it = makeObject();
        final List<E> actual = new ArrayList<>();
        it.forEachRemaining(actual::add);
        assertEquals(expected, actual);
    }

    /**
     * Allows subclasses to add complex cross verification
     */
    public void verify() {
        // do nothing
    }

    /**
     * test checking that no tuples are returned when at least one of the lists is empty
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
