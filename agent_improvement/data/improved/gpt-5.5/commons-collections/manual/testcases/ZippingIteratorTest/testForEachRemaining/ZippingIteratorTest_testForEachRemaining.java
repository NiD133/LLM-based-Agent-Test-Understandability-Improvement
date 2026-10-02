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

    private List<Integer> evens;
    private List<Integer> odds;
    private List<Integer> fibonacci;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
        for (int value = 0; value < 20; value++) {
            if (value % 2 == 0) {
                evens.add(value);
            } else {
                odds.add(value);
            }
        }

        fibonacci = new ArrayList<>();
        fibonacci.add(1);
        fibonacci.add(1);
        fibonacci.add(2);
        fibonacci.add(3);
        fibonacci.add(5);
        fibonacci.add(8);
        fibonacci.add(13);
        fibonacci.add(21);
    }

    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fibonacci.iterator());
    }

    /**
     * Tests {@link Iterator#forEachRemaining(java.util.function.Consumer)}.
     */
    @Test
    void testForEachRemaining() {
        final List<Integer> expected = IteratorUtils.toList(makeObject());
        final Iterator<Integer> iterator = makeObject();
        final List<Integer> actual = new ArrayList<>();

        iterator.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }
}
