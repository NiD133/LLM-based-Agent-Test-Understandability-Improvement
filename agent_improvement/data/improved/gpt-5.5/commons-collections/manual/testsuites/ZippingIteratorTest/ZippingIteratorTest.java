/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test suite for {@link ZippingIterator}.
 */
@SuppressWarnings("boxing")
class ZippingIteratorTest extends AbstractIteratorTest<Integer> {

    private ArrayList<Integer> evens;

    private ArrayList<Integer> odds;
    private ArrayList<Integer> fib;

    @Override
    @SuppressWarnings("unchecked")
    public ZippingIterator<Integer> makeEmptyIterator() {
        return new ZippingIterator<>(IteratorUtils.<Integer>emptyIterator());
    }

    @Override
    public ZippingIterator<Integer> makeObject() {
        return new ZippingIterator<>(evens.iterator(), odds.iterator(), fib.iterator());
    }

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

    private void assertHasNextThenNext(final ZippingIterator<Integer> iter, final Integer expected) {
        assertTrue(iter.hasNext());
        assertEquals(expected, iter.next());
    }

    private void assertNextValues(final ZippingIterator<Integer> iter, final int... expectedValues) {
        for (final int expectedValue : expectedValues) {
            assertEquals(Integer.valueOf(expectedValue), iter.next());
        }
    }

    private int removeMatchingValues(final ZippingIterator<Integer> iter, final int startingSize,
            final boolean removeMultiplesOfFour, final boolean removeMultiplesOfThree) {
        int expectedSize = startingSize;
        while (iter.hasNext()) {
            final Object o = iter.next();
            final Integer val = (Integer) o;
            if (removeMultiplesOfFour && val.intValue() % 4 == 0
                    || removeMultiplesOfThree && val.intValue() % 3 == 0) {
                expectedSize--;
                iter.remove();
            }
        }
        return expectedSize;
    }

    @Test
    void testIterateEven() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        for (final Integer even : evens) {
            assertHasNextThenNext(iter, even);
        }
        assertFalse(iter.hasNext());
    }

    @Test
    void testIterateEvenEven() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), evens.iterator());
        for (final Integer even : evens) {
            assertHasNextThenNext(iter, even);
            assertHasNextThenNext(iter, even);
        }
        assertFalse(iter.hasNext());
    }

    @Test
    void testIterateEvenOdd() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        for (int i = 0; i < 20; i++) {
            assertHasNextThenNext(iter, Integer.valueOf(i));
        }
        assertFalse(iter.hasNext());
    }

    @Test
    void testIterateFibEvenOdd() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(fib.iterator(), evens.iterator(), odds.iterator());

        assertNextValues(iter,
                1, 0, 1,
                1, 2, 3,
                2, 4, 5,
                3, 6, 7,
                5, 8, 9,
                8, 10, 11,
                13, 12, 13,
                21, 14, 15,
                16, 17,
                18, 19);

        assertFalse(iter.hasNext());
    }

    @Test
    void testIterateOddEven() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(odds.iterator(), evens.iterator());
        for (int i = 0, j = 0; i < 20; i++) {
            assertTrue(iter.hasNext());
            final int val = iter.next();
            if (i % 2 == 0) {
                assertEquals(odds.get(j).intValue(), val);
            } else {
                assertEquals(evens.get(j).intValue(), val);
                j++;
            }
        }
        assertFalse(iter.hasNext());
    }

    @Test
    void testRemoveFromDouble() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        final int expectedSize = removeMatchingValues(iter, evens.size() + odds.size(), true, true);
        assertEquals(expectedSize, evens.size() + odds.size());
    }

    @Test
    void testRemoveFromSingle() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        final int expectedSize = removeMatchingValues(iter, evens.size(), true, false);
        assertEquals(expectedSize, evens.size());
    }

}
