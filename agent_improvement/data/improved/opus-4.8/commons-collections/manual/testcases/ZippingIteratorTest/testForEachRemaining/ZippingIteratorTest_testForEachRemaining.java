package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ZippingIterator#forEachRemaining(java.util.function.Consumer)}.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testForEachRemaining {

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

    /**
     * Creates a {@link ZippingIterator} that interleaves the evens, odds and
     * Fibonacci lists.
     */
    private ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    /**
     * {@code forEachRemaining} should visit exactly the same elements, in the
     * same order, as a straightforward {@code next()} traversal.
     */
    @Test
    void testForEachRemaining() {
        final List<Integer> expected = IteratorUtils.toList(makeObject());

        final List<Integer> actual = new ArrayList<>();
        final Iterator<Integer> it = makeObject();
        it.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
