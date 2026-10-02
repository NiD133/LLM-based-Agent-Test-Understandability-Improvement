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
    private ArrayList<Integer> fibonacciNumbers;

    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fibonacciNumbers.iterator());
    }

    @BeforeEach
    public void setUp() throws Exception {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int value = 0; value < 20; value++) {
            if (0 == value % 2) {
                evens.add(value);
            } else {
                odds.add(value);
            }
        }

        fibonacciNumbers = new ArrayList<>();
        fibonacciNumbers.add(1);
        fibonacciNumbers.add(1);
        fibonacciNumbers.add(2);
        fibonacciNumbers.add(3);
        fibonacciNumbers.add(5);
        fibonacciNumbers.add(8);
        fibonacciNumbers.add(13);
        fibonacciNumbers.add(21);
    }

    public boolean supportsRemove() {
        return true;
    }

    public void verify() {
        // Hook retained from the original iterator contract test.
    }

    @Test
    void testRemove() {
        final Iterator<Integer> iterator = makeObject();
        if (!supportsRemove()) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            return;
        }

        assertThrows(IllegalStateException.class, iterator::remove);
        verify();

        iterator.next();
        iterator.remove();

        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
