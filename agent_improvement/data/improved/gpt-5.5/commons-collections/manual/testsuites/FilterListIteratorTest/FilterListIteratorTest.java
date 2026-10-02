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
 * Tests the FilterListIterator class.
 */
@SuppressWarnings("boxing")
class FilterListIteratorTest {

    private static final int TEST_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> evens;
    private ArrayList<Integer> threes;
    private ArrayList<Integer> fours;
    private ArrayList<Integer> sixes;

    private Predicate<Integer> truePred;
    private Predicate<Integer> falsePred;
    private Predicate<Integer> evenPred;
    private Predicate<Integer> oddPred;
    private Predicate<Integer> threePred;
    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; i++) {
            list.add(Integer.valueOf(i));
            addIfDivisibleBy(i, 2, evens);
            addIfNotDivisibleBy(i, 2, odds);
            addIfDivisibleBy(i, 3, threes);
            addIfDivisibleBy(i, 4, fours);
            addIfDivisibleBy(i, 6, sixes);
        }

        truePred = x -> true;

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
     * Test for https://issues.apache.org/jira/browse/COLLECTIONS-360.
     */
    @Test
    void testCollections360() throws Throwable {
        final Collection<Predicate<Object>> predicates = new GrowthList<>();
        final Predicate<Object> anyPredicate = PredicateUtils.anyPredicate(predicates);
        final FilterListIterator<Object> iteratorWithNoList = new FilterListIterator<>(anyPredicate);
        assertFalse(iteratorWithNoList.hasNext());
        final FilterListIterator<Object> secondIteratorWithNoList = new FilterListIterator<>(anyPredicate);
        assertFalse(secondIteratorWithNoList.hasPrevious());
    }

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), evenPred);
        walkLists(evens, filtered);
    }

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

    @Test
    void testManual() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);

        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());

        assertFalse(filtered.hasPrevious());

        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        assertFalse(filtered.hasNext());

        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());

        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(0), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.next());

        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());

        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
    }

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
        walkLists(sixes, new FilterListIterator<>(filtered, truePred));
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

    @Test
    void testWalkLists() {
        walkLists(list, list.listIterator());
    }

    private void addIfDivisibleBy(final int value, final int divisor, final List<Integer> target) {
        if (value % divisor == 0) {
            target.add(Integer.valueOf(value));
        }
    }

    private void addIfNotDivisibleBy(final int value, final int divisor, final List<Integer> target) {
        if (value % divisor != 0) {
            target.add(Integer.valueOf(value));
        }
    }

    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());
        final Object secondExpectedNext = expected.next();
        final Object secondTestingNext = testing.next();
        assertEquals(secondExpectedNext, secondTestingNext);
        final Object expectedPrevious = expected.previous();
        final Object testingPrevious = testing.previous();
        assertEquals(secondExpectedNext, expectedPrevious);
        assertEquals(secondTestingNext, testingPrevious);
    }

    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());
        final Object secondExpectedPrevious = expected.previous();
        final Object secondTestingPrevious = testing.previous();
        assertEquals(secondExpectedPrevious, secondTestingPrevious);
        final Object expectedNext = expected.next();
        final Object testingNext = testing.next();
        assertEquals(secondExpectedPrevious, testingNext);
        assertEquals(secondExpectedPrevious, expectedNext);
        assertEquals(secondTestingPrevious, testingNext);
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertSameIndexes(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertSameIndexes(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardBackwardForward(expected, testing);
        walkBackward(expected, testing);
        walkVariableDistances(list, expected, testing);
        walkRandomly(expected, testing);
    }

    private void walkForwardBackwardForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertSameIndexes(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkVariableDistances(final List<E> list, final ListIterator<E> expected,
            final ListIterator<E> testing) {
        for (int i = 0; i < list.size(); i++) {
            walkForwardSteps(expected, testing, i);
            walkBackwardSteps(expected, testing, i / 2);
            walkForwardSteps(expected, testing, i / 2);
            walkBackwardSteps(expected, testing, i);
        }
    }

    private void walkForwardSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertSameIndexes(expected, testing);
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackwardSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertSameIndexes(expected, testing);
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkRandomly(final ListIterator<?> expected, final ListIterator<?> testing) {
        final StringBuilder walkDescription = new StringBuilder(RANDOM_WALK_STEPS);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
            if (random.nextBoolean()) {
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescription.toString());
                }
            }
            assertSameIndexes(expected, testing, walkDescription.toString());
        }
    }

    private void assertSameIndexes(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void assertSameIndexes(final ListIterator<?> expected, final ListIterator<?> testing,
            final String message) {
        assertEquals(expected.nextIndex(), testing.nextIndex(), message);
        assertEquals(expected.previousIndex(), testing.previousIndex(), message);
    }

}
