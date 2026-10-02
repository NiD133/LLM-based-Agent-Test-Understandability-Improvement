package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ZippingIterator#remove()} correctly delegates removal to the
 * underlying child iterator that produced the element, when iterating over two lists.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testRemoveFromDouble {

    /** Even numbers: 0, 2, 4, ..., 18 */
    private ArrayList<Integer> evens;

    /** Odd numbers: 1, 3, 5, ..., 19 */
    private ArrayList<Integer> odds;

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
    }

    /**
     * Verifies that removing elements via a ZippingIterator over two lists correctly
     * removes them from the backing lists. Elements divisible by 4 or by 3 are removed;
     * the combined size of both lists is checked to equal the number of non-removed elements.
     */
    @Test
    void testRemoveFromDouble() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());

        int expectedRemainingSize = evens.size() + odds.size();
        while (iter.hasNext()) {
            final Integer val = iter.next();
            if (isDivisibleBy4Or3(val)) {
                iter.remove();
                expectedRemainingSize--;
            }
        }

        assertEquals(expectedRemainingSize, evens.size() + odds.size());
    }

    private boolean isDivisibleBy4Or3(final int n) {
        return n % 4 == 0 || n % 3 == 0;
    }
}
