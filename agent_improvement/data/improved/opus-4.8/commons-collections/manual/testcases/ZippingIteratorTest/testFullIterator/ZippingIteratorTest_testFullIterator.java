package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link ZippingIterator} built over several non-empty source
 * iterators behaves correctly across a full traversal: it reports elements,
 * yields every element without error, and fails once exhausted.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testFullIterator {

    /** Even numbers in [0, 20): 0, 2, 4, ... 18. */
    private ArrayList<Integer> evens;

    /** Odd numbers in [0, 20): 1, 3, 5, ... 19. */
    private ArrayList<Integer> odds;

    /** The first eight Fibonacci numbers. */
    private ArrayList<Integer> fib;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                evens.add(i);
            } else {
                odds.add(i);
            }
        }
        fib = new ArrayList<>();
        fib.add(1);
        fib.add(1);
        fib.add(2);
        fib.add(3);
        fib.add(5);
        fib.add(8);
        fib.add(13);
        fib.add(21);
    }

    /** Creates a zipping iterator that interleaves the three populated lists. */
    private ZippingIterator<Integer> makeFullIterator() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    /**
     * A fully populated zipping iterator must expose its elements, traverse all
     * of them without throwing, then throw {@link NoSuchElementException} once
     * exhausted while still producing a non-null {@code toString()}.
     */
    @Test
    void testFullIterator() {
        final Iterator<Integer> it = makeFullIterator();

        // There is at least one element, and reading it must succeed.
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Consume every remaining element.
        while (it.hasNext()) {
            it.next();
        }

        // Once exhausted, next() must fail.
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");
        assertNotNull(it.toString());
    }
}
