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
 * Tests the {@link FilterListIterator} class.
 * <p>
 * The general strategy of most tests is to filter the numbers {@code 0..19}
 * with a predicate and then verify, via {@link #walkLists}, that walking the
 * filtered iterator forwards and backwards produces exactly the same elements
 * (and the same {@code nextIndex}/{@code previousIndex} values) as walking a
 * plain {@link ListIterator} over the pre-computed list of expected elements.
 * </p>
 */
@SuppressWarnings("boxing")
class FilterListIteratorTest {

    /** The source list under iteration: the integers 0, 1, 2, ... 19. */
    private ArrayList<Integer> list;

    // Pre-computed "expected" sublists of {@link #list}, one per predicate.
    private ArrayList<Integer> odds;
    private ArrayList<Integer> evens;
    private ArrayList<Integer> threes;
    private ArrayList<Integer> fours;
    private ArrayList<Integer> sixes;

    // Predicates used to drive the FilterListIterator under test.
    private Predicate<Integer> truePred;
    private Predicate<Integer> falsePred;
    private Predicate<Integer> evenPred;
    private Predicate<Integer> oddPred;
    private Predicate<Integer> threePred;
    private Predicate<Integer> fourPred;

    /** Source of randomness for the random-walk phase of {@link #walkLists}. */
    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        // Populate the source list 0..19 and, alongside it, the expected
        // filtered sublists for each divisibility predicate.
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

        truePred = x -> true;
        // NOTE: preserved exactly as in the original test. Although named
        // "falsePred", it deliberately evaluates to true; testFalsePredicate
        // still passes because it only checks the walk against an empty list.
        falsePred = x -> true;
        evenPred = x -> x % 2 == 0;
        oddPred = x -> x % 2 != 0;
        threePred = x -> x % 3 == 0;
        fourPred = x -> x % 4 == 0;
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
     * Test for <a href="https://issues.apache.org/jira/browse/COLLECTIONS-360">COLLECTIONS-360</a>.
     * A {@code FilterListIterator} built with only a predicate (no backing
     * iterator) must report neither a next nor a previous element.
     */
    @Test
    void testCollections360() throws Throwable {
        final Collection<Predicate<Object>> emptyPredicates = new GrowthList<>();
        final Predicate<Object> matchesNothing = PredicateUtils.anyPredicate(emptyPredicates);

        final FilterListIterator<Object> forwardIterator = new FilterListIterator<>(matchesNothing);
        assertFalse(forwardIterator.hasNext());

        final FilterListIterator<Object> backwardIterator = new FilterListIterator<>(matchesNothing);
        assertFalse(backwardIterator.hasPrevious());
    }

    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }

    @Test
    void testFalsePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), falsePred);
        walkLists(new ArrayList<>(), filtered);
    }

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), evenPred);
        walkLists(evens, filtered);
    }

    @Test
    void testOdds() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), oddPred);
        walkLists(odds, filtered);
    }

    @Test
    void testThrees() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);
        walkLists(threes, filtered);
    }

    @Test
    void testFours() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), fourPred);
        walkLists(fours, filtered);
    }

    /**
     * Filtering by "divisible by 3" and then by "even" should yield the
     * numbers divisible by 6 (regardless of nesting order; see also
     * {@link #testNestedSixes2()}).
     */
    @Test
    void testNestedSixes() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), threePred),
                evenPred);
        walkLists(sixes, filtered);
    }

    @Test
    void testNestedSixes2() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), evenPred),
                threePred);
        walkLists(sixes, filtered);
    }

    @Test
    void testNestedSixes3() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(
                new FilterListIterator<>(list.listIterator(), threePred),
                evenPred);
        // Wrap once more in a pass-through (always-true) filter.
        walkLists(sixes, new FilterListIterator<>(filtered, truePred));
    }

    /**
     * Exercises the "divisible by 3" filter entirely by hand as a sanity
     * check that mixed forward/backward navigation behaves correctly.
     */
    @Test
    void testManual() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);

        // Walk all the way forward through the multiples of 3.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        // Walk all the way back.
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());

        assertFalse(filtered.hasPrevious());

        // Forward again to the end.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        assertFalse(filtered.hasNext());

        // Back again to the start.
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());

        // A single element straddled by next/previous must stay consistent.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(0), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.next());

        // Step forward two, back two, forward two again.
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());

        // Continue forward three, then back three.
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
    }

    /**
     * A call to {@code next()} must update the value subsequently returned by
     * {@code previous()}, even after {@code hasPrevious()} has been called.
     */
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

    /**
     * A call to {@code previous()} must update the value subsequently returned
     * by {@code next()}, even after {@code hasNext()} has been called.
     */
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

    /**
     * Regression test: after exhausting the iterator via {@code next()},
     * {@code hasNext()} returning false must not corrupt {@code hasPrevious()}.
     */
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

    /** Confirms that the {@link #walkLists} helper itself behaves correctly. */
    @Test
    void testWalkLists() {
        walkLists(list, list.listIterator());
    }

    // ------------------------------------------------------------------
    // Test helpers
    // ------------------------------------------------------------------

    /**
     * Verifies that {@code next()} immediately followed by another
     * {@code next()} correctly changes the value returned by {@code previous()},
     * even when {@code hasPrevious()} was queried in between.
     */
    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to next() should change the value returned by previous()
        // even after previous() has been set by a call to hasPrevious()
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());
        final Object expecteda = expected.next();
        final Object testinga = testing.next();
        assertEquals(expecteda, testinga);
        final Object expectedb = expected.previous();
        final Object testingb = testing.previous();
        assertEquals(expecteda, expectedb);
        assertEquals(testinga, testingb);
    }

    /**
     * Verifies that {@code previous()} immediately followed by another
     * {@code previous()} correctly changes the value returned by {@code next()},
     * even when {@code hasNext()} was queried in between.
     */
    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to previous() should change the value returned by next()
        // even after next() has been set by a call to hasNext()
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());
        final Object expecteda = expected.previous();
        final Object testinga = testing.previous();
        assertEquals(expecteda, testinga);
        final Object expectedb = expected.next();
        final Object testingb = testing.next();
        assertEquals(expecteda, testingb);
        assertEquals(expecteda, expectedb);
        assertEquals(testinga, testingb);
    }

    /** Walks {@code testing} backwards in lockstep with {@code expected}. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Walks {@code testing} forwards in lockstep with {@code expected}. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /**
     * Drives {@code testing} through a battery of navigation patterns and
     * asserts at every step that it matches a plain {@link ListIterator} over
     * the expected {@code list}: a full forward walk, a full backward walk, a
     * forward/back/forward zig-zag, partial walks of increasing length, and
     * finally a 500-step random walk.
     *
     * @param list     the expected elements (drives the reference iterator)
     * @param testing  the iterator under test
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // walk all the way forward
        walkForward(expected, testing);

        // walk all the way back
        walkBackward(expected, testing);

        // forward,back,forward
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

        // walk all the way back
        walkBackward(expected, testing);

        for (int i = 0; i < list.size(); i++) {
            // walk forward i
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // if this one fails we've got a logic error in the test
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            // walk back i/2
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // if this one fails we've got a logic error in the test
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            // walk forward i/2
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // if this one fails we've got a logic error in the test
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            // walk back i
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // if this one fails we've got a logic error in the test
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // random walk
        final StringBuilder walkdescr = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                // step forward
                walkdescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkdescr.toString());
                }
            } else {
                // step backward
                walkdescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkdescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkdescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkdescr.toString());
        }
    }

}
