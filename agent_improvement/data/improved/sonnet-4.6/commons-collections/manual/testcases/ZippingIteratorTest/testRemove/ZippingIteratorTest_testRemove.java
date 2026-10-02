package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemove {

    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> fib;

    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    @BeforeEach
    public void setUp() throws Exception {
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

    public boolean supportsRemove() {
        return true;
    }

    /**
     * Verifies the contract of remove():
     * - throws UnsupportedOperationException if remove is not supported at all
     * - throws IllegalStateException when called before any next() invocation
     * - succeeds when called once after next()
     * - throws IllegalStateException when called twice in a row (no next() in between)
     */
    @Test
    void testRemove() {
        final Iterator<Integer> iterator = makeObject();

        if (!supportsRemove()) {
            // iterator does not support remove — every call must throw
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            return;
        }

        // remove() before next() must throw: no element has been returned yet
        assertThrows(IllegalStateException.class, iterator::remove);

        // advance the iterator so there is a current element available for removal
        iterator.next();

        // remove() after next() must succeed
        iterator.remove();

        // remove() again without an intervening next() must throw:
        // the current element was already removed
        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
