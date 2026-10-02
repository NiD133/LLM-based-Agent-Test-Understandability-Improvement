package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testForEachRemaining {

    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;
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

    private ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

    /**
     * Verifies that {@link Iterator#forEachRemaining} visits every interleaved element
     * in the same order as sequential {@link Iterator#next} calls would.
     */
    @Test
    void testForEachRemaining() {
        List<Integer> expected = IteratorUtils.toList(makeObject());

        List<Integer> actual = new ArrayList<>();
        Iterator<Integer> it = makeObject();
        it.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
