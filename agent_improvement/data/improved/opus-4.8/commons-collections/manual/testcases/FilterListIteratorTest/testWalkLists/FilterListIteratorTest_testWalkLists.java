package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Sanity check for the {@code walkLists} test harness used by
 * {@link FilterListIterator} tests.
 * <p>
 * {@code walkLists} drives a "testing" {@link ListIterator} through a series of
 * forward, backward and random walks while comparing every step against an
 * "expected" reference iterator. This test feeds it two iterators over the same
 * list, so the comparison must always succeed; if it does not, the harness
 * itself is broken.
 * </p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testWalkLists {

    /** Number of elements placed in the list under test. */
    private static final int LIST_SIZE = 20;

    /** Number of steps taken during the random-walk phase of {@code walkLists}. */
    private static final int RANDOM_WALK_STEPS = 500;

    /** The list whose iterator is exercised: the integers {@code 0..LIST_SIZE-1}. */
    private ArrayList<Integer> list;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(i);
        }
    }

    /**
     * Walks both iterators backward to the start, asserting they stay in lockstep
     * on every index and value.
     */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Walks both iterators forward to the end, asserting they stay in lockstep
     * on every index and value.
     */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /**
     * Drives {@code testing} through the same motions as a fresh reference
     * iterator over {@code list}, asserting they agree at every step.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // walk all the way forward, then all the way back
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // forward one, back one, forward one - repeated to the end
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

        // for each i: forward i, back i/2, forward i/2, back i (ends where it started)
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // random walk: step forward or backward at random, comparing as we go.
        // walkDescr records the path ("+"/"-") so a failure message can reproduce it.
        final StringBuilder walkDescr = new StringBuilder(RANDOM_WALK_STEPS);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
            if (random.nextBoolean()) {
                // step forward
                walkDescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescr.toString());
                }
            } else {
                // step backward
                walkDescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescr.toString());
        }
    }

    @Test
    void testWalkLists() {
        // this just confirms that our walkLists method works OK
        walkLists(list, list.listIterator());
    }
}
