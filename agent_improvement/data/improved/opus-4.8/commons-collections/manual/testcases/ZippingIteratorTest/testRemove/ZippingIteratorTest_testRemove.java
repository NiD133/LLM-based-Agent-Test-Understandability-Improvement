package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ZippingIterator#remove()}.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemove {

    /** Even numbers in the range [0, 20). */
    private List<Integer> evens;

    /** Odd numbers in the range [0, 20). */
    private List<Integer> odds;

    /** The first eight Fibonacci numbers. */
    private List<Integer> fib;

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
        for (final int value : new int[] {1, 1, 2, 3, 5, 8, 13, 21}) {
            fib.add(value);
        }
    }

    /**
     * Creates a ZippingIterator that interleaves the even, odd and Fibonacci lists.
     */
    private ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    /**
     * Verifies the contract of {@link ZippingIterator#remove()}:
     * <ul>
     *   <li>calling remove() before next() throws IllegalStateException,</li>
     *   <li>calling remove() once after next() succeeds,</li>
     *   <li>calling remove() a second time without an intervening next() throws
     *       IllegalStateException.</li>
     * </ul>
     */
    @Test
    void testRemove() {
        final ZippingIterator<Integer> it = makeObject();

        // remove() is illegal until next() has returned an element
        assertThrows(IllegalStateException.class, it::remove);

        // remove() right after next() removes the element just returned
        it.next();
        it.remove();

        // a second remove() without another next() is illegal again
        assertThrows(IllegalStateException.class, it::remove);
    }
}
