package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromSingle {

    private ArrayList<Integer> evens;

    private ArrayList<Integer> odds;

    private ArrayList<Integer> fib;

    @BeforeEach
    public void setUp() throws Exception {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (0 == i % 2) {
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

    @Test
    void testRemoveFromSingle() {
        final ZippingIterator<Integer> iterator = new ZippingIterator<>(evens.iterator());
        int expectedSize = evens.size();

        while (iterator.hasNext()) {
            final Object nextValue = iterator.next();
            final Integer value = (Integer) nextValue;
            if (value.intValue() % 4 == 0) {
                expectedSize--;
                iterator.remove();
            }
        }

        assertEquals(expectedSize, evens.size());
    }
}
