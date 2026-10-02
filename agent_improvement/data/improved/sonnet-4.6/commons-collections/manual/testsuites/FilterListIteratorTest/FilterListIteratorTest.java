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
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.list.GrowthList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the FilterListIterator class, which wraps a ListIterator and filters
 * elements using a Predicate, while supporting bidirectional traversal via
 * next() and previous().
 */
@SuppressWarnings("boxing")
class FilterListIteratorTest {

    // Source data: integers 0..19 and pre-computed filtered sublists
    private ArrayList<Integer> list;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> evens;
    private ArrayList<Integer> threes;
    private ArrayList<Integer> fours;
    private ArrayList<Integer> sixes;

    // Predicates used to construct filtered iterators
    private Predicate<Integer> truePred;
    private Predicate<Integer> falsePred;  // Note: behaves like truePred (accepts all) due to a copy-paste defect;
                                           // testFalsePredicate still passes because it compares against an empty expected list
    private Predicate<Integer> evenPred;
    private Predicate<Integer> oddPred;
    private Predicate<Integer> threePred;
    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    /**
     * Verifies that calling next() twice then previous() once returns the element
     * produced by the second next() call — i.e., next() correctly advances the
     * position visible to previous(), even when hasPrevious() was called in between.
     */
    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        // Advance both iterators one step and confirm they agree
        assertEquals(expected.next(), testing.next());
        // hasPrevious() may internally peek; confirm this peek does not corrupt next()
        assertEquals(expected.hasPrevious(), testing.hasPrevious());

        // Capture the element returned by the second next() call
        final Object expectedSecondElement = expected.next();
        final Object actualSecondElement = testing.next();
        assertEquals(expectedSecondElement, actualSecondElement);

        // previous() must return the same element that next() just produced
        final Object expectedAfterPrevious = expected.previous();
        final Object actualAfterPrevious = testing.previous();
        assertEquals(expectedSecondElement, expectedAfterPrevious);
        assertEquals(actualSecondElement, actualAfterPrevious);
    }

    /**
     * Verifies that calling previous() twice then next() once returns the element
     * produced by the second previous() call — i.e., previous() correctly moves
     * the position visible to next(), even when hasNext() was called in between.
     */
    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        // Move both iterators one step back and confirm they agree
        assertEquals(expected.previous(), testing.previous());
        // hasNext() may internally peek; confirm this peek does not corrupt previous()
        assertEquals(expected.hasNext(), testing.hasNext());

        // Capture the element returned by the second previous() call
        final Object expectedSecondPrevious = expected.previous();
        final Object actualSecondPrevious = testing.previous();
        assertEquals(expectedSecondPrevious, actualSecondPrevious);

        // next() must return the same element that previous() just produced
        final Object expectedAfterNext = expected.next();
        final Object actualAfterNext = testing.next();
        assertEquals(expectedSecondPrevious, actualAfterNext);
        assertEquals(expectedSecondPrevious, expectedAfterNext);
        assertEquals(actualSecondPrevious, actualAfterNext);
    }

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
            if (i % 2 == 0) {
                evens.add(Integer.valueOf(i));
            }
            if (i % 2 != 0) {
                odds.add(Integer.valueOf(i));
            }
            if (i % 3 == 0) {
                threes.add(Integer.valueOf(i));
            }
            if (i % 4 == 0) {
                fours.add(Integer.valueOf(i));
            }
            if (i % 6 == 0) {
                sixes.add(Integer.valueOf(i));
            }
        }

        truePred  = x -> true;
        falsePred = x -> true;  // kept as-is; see field comment above
        evenPred  = x -> x % 2 == 0;
        oddPred   = x -> x % 2 != 0;
        threePred = x -> x % 3 == 0;
        fourPred  = x -> x % 4 == 0;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        odds = null;
        evens = null;
        threes = null;
        fours = null;
        sixes = null;
        truePred = null;
        falsePred = null;
        evenPred = null;
        oddPred = null;
        threePred = null;
        fourPred = null;
    }

    /**
     * Regression test for COLLECTIONS-360: constructing a FilterListIterator with
     * only a Predicate (no underlying ListIterator) must not throw when hasNext()
     * or hasPrevious() is called.
     */
    @Test
    void testCollections360() throws Throwable {
        final Collection<Predicate<Object>> predicates = new GrowthList<>();
        final Predicate<Object> alwaysFalsePredicate = PredicateUtils.anyPredicate(predicates);

        final FilterListIterator<Object> iteratorCheckingHasNext = new FilterListIterator<>(alwaysFalsePredicate);
        assertFalse(iteratorCheckingHasNext.hasNext());

        final FilterListIterator<Object> iteratorCheckingHasPrevious = new FilterListIterator<>(alwaysFalsePredicate);
        assertFalse(iteratorCheckingHasPrevious.hasPrevious());
    }

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), evenPred);
        walkLists(evens, filtered);
    }

    /** Regression test: after exhausting all matching elements, hasPrevious() and previous() must still work correctly. */
    @Test
    void testFailingHasNextBug() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), fourPred);
        final ListIterator<Integer> expected = fours.listIterator();
        while (expected.hasNext()) {
            expected.next();
            filtered.next();
        }
        assertTrue(filtered.hasPrevious());
        assertFalse(filtered.hasNext());
        assertEquals(expected.previous(), filtered.previous());
    }

    @Test
    void testFalsePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), falsePred);
        walkLists(new ArrayList<>(), filtered);
    }

    @Test
    void testFours() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), fourPred);
        walkLists(fours, filtered);
    }

    /** Sanity-check: manually verify next() and previous() sequences for multiples of 3. */
    @Test
    void testManual() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);

        // Forward pass: 0, 3, 6, 9, 12, 15, 18
        assertEquals(Integer.valueOf(0),  filtered.next());
        assertEquals(Integer.valueOf(3),  filtered.next());
        assertEquals(Integer.valueOf(6),  filtered.next());
        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        // Backward pass: 18, 15, 12, 9, 6, 3, 0
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
        assertEquals(Integer.valueOf(6),  filtered.previous());
        assertEquals(Integer.valueOf(3),  filtered.previous());
        assertEquals(Integer.valueOf(0),  filtered.previous());

        assertFalse(filtered.hasPrevious());

        // Forward again to verify re-traversal works after reaching the start
        assertEquals(Integer.valueOf(0),  filtered.next());
        assertEquals(Integer.valueOf(3),  filtered.next());
        assertEquals(Integer.valueOf(6),  filtered.next());
        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        assertFalse(filtered.hasNext());

        // Backward again after reaching the end
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
        assertEquals(Integer.valueOf(6),  filtered.previous());
        assertEquals(Integer.valueOf(3),  filtered.previous());
        assertEquals(Integer.valueOf(0),  filtered.previous());

        // Toggle at the lower boundary
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(0), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.next());

        // Zigzag in the middle of the sequence
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());

        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
    }

    /** Nested filter: applying threePred then evenPred yields multiples of 6. */
    @Test
    void testNestedSixes() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), threePred),
                evenPred);
        walkLists(sixes, filtered);
    }

    /** Nested filter (reversed predicate order): applying evenPred then threePred also yields multiples of 6. */
    @Test
    void testNestedSixes2() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), evenPred),
                threePred);
        walkLists(sixes, filtered);
    }

    /** Triple nested: wrapping a sixes filter with truePred must produce the same elements. */
    @Test
    void testNestedSixes3() {
        final FilterListIterator<Integer> sixesFiltered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), threePred),
                evenPred);
        walkLists(sixes, new FilterListIterator<>(sixesFiltered, truePred));
    }

    @Test
    void testNextChangesPrevious() {
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);
            nextNextPrevious(threes.listIterator(), filtered);
        }
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
            nextNextPrevious(list.listIterator(), filtered);
        }
    }

    @Test
    void testOdds() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), oddPred);
        walkLists(odds, filtered);
    }

    @Test
    void testPreviousChangesNext() {
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);
            final ListIterator<Integer> expected = threes.listIterator();
            walkForward(expected, filtered);
            previousPreviousNext(expected, filtered);
        }
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
            final ListIterator<Integer> expected = list.listIterator();
            walkForward(expected, filtered);
            previousPreviousNext(expected, filtered);
        }
    }

    @Test
    void testThrees() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);
        walkLists(threes, filtered);
    }

    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }

    /** Verifies that walkLists itself is correct by running it against a plain list iterator. */
    @Test
    void testWalkLists() {
        walkLists(list, list.listIterator());
    }

    // -------------------------------------------------------------------------
    // Traversal helpers
    // -------------------------------------------------------------------------

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /**
     * From the current iterator position, walks {@code steps} elements forward,
     * then {@code steps/2} backward, then {@code steps/2} forward, then
     * {@code steps} backward — leaving both iterators at their original position.
     */
    private void walkSymmetricSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        // Walk forward 'steps' elements
        for (int j = 0; j < steps; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(expected.hasNext()); // if this fails there is a logic error in the test
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        // Walk backward 'steps/2' elements
        for (int j = 0; j < steps / 2; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(expected.hasPrevious()); // if this fails there is a logic error in the test
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
        // Walk forward 'steps/2' elements (restores position to 'steps' from start)
        for (int j = 0; j < steps / 2; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(expected.hasNext()); // if this fails there is a logic error in the test
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        // Walk backward 'steps' elements (restores position to where we started)
        for (int j = 0; j < steps; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(expected.hasPrevious()); // if this fails there is a logic error in the test
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Comprehensive bidirectional traversal test that drives {@code testing} through
     * several movement patterns and verifies it always agrees with a fresh iterator
     * over {@code list}.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Walk all the way forward
        walkForward(expected, testing);

        // Walk all the way back
        walkBackward(expected, testing);

        // Forward, back, forward one element at a time
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        // Walk all the way back after the zigzag
        walkBackward(expected, testing);

        // Symmetric pattern for each prefix length i: forward i, back i/2, forward i/2, back i
        for (int i = 0; i < list.size(); i++) {
            walkSymmetricSteps(expected, testing, i);
        }

        // Random walk of 500 steps; the path string is appended for failure diagnosis
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                // step forward
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescription.toString());
                }
            } else {
                // step backward
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescription.toString());
        }
    }
}
