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
 * <p>
 * A {@link ZippingIterator} interleaves the elements of its child iterators in
 * round-robin order, so a tour over {@code [A, B, C]} yields
 * {@code A0, B0, C0, A1, B1, C1, ...}. When a child runs out of elements it is
 * dropped and the remaining children keep being interleaved.
 * </p>
 * <p>
 * The tests share three fixtures built in {@link #setUp()}:
 * </p>
 * <ul>
 *   <li>{@link #evens} = {@code [0, 2, 4, ..., 18]} (10 elements)</li>
 *   <li>{@link #odds}  = {@code [1, 3, 5, ..., 19]} (10 elements)</li>
 *   <li>{@link #fib}   = {@code [1, 1, 2, 3, 5, 8, 13, 21]} (8 elements)</li>
 * </ul>
 */
@SuppressWarnings("boxing")
class ZippingIteratorTest extends AbstractIteratorTest<Integer> {

    /** Even numbers in {@code [0, 20)}: {@code [0, 2, 4, ..., 18]}. */
    private ArrayList<Integer> evens;

    /** Odd numbers in {@code [0, 20)}: {@code [1, 3, 5, ..., 19]}. */
    private ArrayList<Integer> odds;

    /** The first eight Fibonacci numbers: {@code [1, 1, 2, 3, 5, 8, 13, 21]}. */
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
     * Zipping a single iterator simply replays that iterator unchanged.
     */
    @Test
    void testIterateEven() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        for (final Integer even : evens) {
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Zipping the same iterator twice yields each even number repeated back to
     * back: {@code [0, 0, 2, 2, 4, 4, ...]}.
     */
    @Test
    void testIterateEvenEven() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), evens.iterator());
        for (final Integer even : evens) {
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Interleaving evens with odds reproduces the natural order
     * {@code [0, 1, 2, 3, ..., 19]}.
     */
    @Test
    void testIterateEvenOdd() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        for (int expected = 0; expected < 20; expected++) {
            assertTrue(iter.hasNext());
            assertEquals(Integer.valueOf(expected), iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Interleaves three iterators of differing lengths. Once {@link #fib}
     * (the shortest, 8 elements) is exhausted, the round-robin continues over
     * just {@link #evens} and {@link #odds} until they too run out. Each
     * expected value is annotated with the child it comes from.
     */
    @Test
    void testIterateFibEvenOdd() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(fib.iterator(), evens.iterator(), odds.iterator());

        // While all three children have elements: fib, even, odd, repeat.
        assertEquals(Integer.valueOf(1), iter.next());  // fib    1
        assertEquals(Integer.valueOf(0), iter.next());  // even   0
        assertEquals(Integer.valueOf(1), iter.next());  // odd    1
        assertEquals(Integer.valueOf(1), iter.next());  // fib    1
        assertEquals(Integer.valueOf(2), iter.next());  // even   2
        assertEquals(Integer.valueOf(3), iter.next());  // odd    3
        assertEquals(Integer.valueOf(2), iter.next());  // fib    2
        assertEquals(Integer.valueOf(4), iter.next());  // even   4
        assertEquals(Integer.valueOf(5), iter.next());  // odd    5
        assertEquals(Integer.valueOf(3), iter.next());  // fib    3
        assertEquals(Integer.valueOf(6), iter.next());  // even   6
        assertEquals(Integer.valueOf(7), iter.next());  // odd    7
        assertEquals(Integer.valueOf(5), iter.next());  // fib    5
        assertEquals(Integer.valueOf(8), iter.next());  // even   8
        assertEquals(Integer.valueOf(9), iter.next());  // odd    9
        assertEquals(Integer.valueOf(8), iter.next());  // fib    8
        assertEquals(Integer.valueOf(10), iter.next()); // even  10
        assertEquals(Integer.valueOf(11), iter.next()); // odd   11
        assertEquals(Integer.valueOf(13), iter.next()); // fib   13
        assertEquals(Integer.valueOf(12), iter.next()); // even  12
        assertEquals(Integer.valueOf(13), iter.next()); // odd   13
        assertEquals(Integer.valueOf(21), iter.next()); // fib   21 (last fib element)

        // fib is now exhausted, so only even and odd remain interleaved.
        assertEquals(Integer.valueOf(14), iter.next()); // even  14
        assertEquals(Integer.valueOf(15), iter.next()); // odd   15
        assertEquals(Integer.valueOf(16), iter.next()); // even  16
        assertEquals(Integer.valueOf(17), iter.next()); // odd   17
        assertEquals(Integer.valueOf(18), iter.next()); // even  18
        assertEquals(Integer.valueOf(19), iter.next()); // odd   19

        assertFalse(iter.hasNext());
    }

    /**
     * Interleaving odds with evens yields {@code [1, 0, 3, 2, 5, 4, ...]}:
     * the odd child is consumed on even positions and the even child on odd
     * positions.
     */
    @Test
    void testIterateOddEven() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(odds.iterator(), evens.iterator());
        for (int position = 0, evenIndex = 0; position < 20; position++) {
            assertTrue(iter.hasNext());
            final int actual = iter.next();
            if (position % 2 == 0) {
                // Even positions come from the odds child; both share index position/2.
                assertEquals(odds.get(evenIndex).intValue(), actual);
            } else {
                assertEquals(evens.get(evenIndex).intValue(), actual);
                evenIndex++;
            }
        }
        assertFalse(iter.hasNext());
    }

    /**
     * {@link ZippingIterator#remove()} deletes the last returned element from
     * its originating child iterator. Removing every multiple of 4 or 3 while
     * zipping evens and odds must shrink the backing lists by exactly the
     * number of elements removed.
     */
    @Test
    void testRemoveFromDouble() {
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator(), odds.iterator());
        int expectedSize = evens.size() + odds.size();
        while (iter.hasNext()) {
            final Integer val = iter.next();
            if (val.intValue() % 4 == 0 || val.intValue() % 3 == 0) {
                expectedSize--;
                iter.remove();
            }
        }
        assertEquals(expectedSize, evens.size() + odds.size());
    }

    /**
     * {@link ZippingIterator#remove()} works with a single child iterator too.
     * Removing every multiple of 4 must shrink the backing list by exactly the
     * number of elements removed.
     */
    @Test
    void testRemoveFromSingle() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());
        int expectedSize = evens.size();
        while (iter.hasNext()) {
            final Integer val = iter.next();
            if (val.intValue() % 4 == 0) {
                expectedSize--;
                iter.remove();
            }
        }
        assertEquals(expectedSize, evens.size());
    }

}
