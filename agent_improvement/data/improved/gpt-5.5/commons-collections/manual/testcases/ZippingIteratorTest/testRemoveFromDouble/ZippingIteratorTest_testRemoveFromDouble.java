package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromDouble {

    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;

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
    }

    @Test
    void testRemoveFromDouble() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        int expectedSize = evens.size() + odds.size();

        while (iter.hasNext()) {
            final Object nextValue = iter.next();
            final Integer value = (Integer) nextValue;
            if (value.intValue() % 4 == 0 || value.intValue() % 3 == 0) {
                expectedSize--;
                iter.remove();
            }
        }

        assertEquals(expectedSize, evens.size() + odds.size());
    }
}
