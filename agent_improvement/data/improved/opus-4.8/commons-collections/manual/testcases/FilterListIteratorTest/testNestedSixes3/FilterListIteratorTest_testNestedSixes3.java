package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that nesting three {@link FilterListIterator}s composes their predicates.
 * <p>
 * The source list is {@code [0, 1, ..., 19]}. Wrapping it in a "divisible by three"
 * filter, then an "even" filter, then a pass-through ("always true") filter should
 * yield exactly the multiples of six, since a number divisible by both 3 and 2 is
 * divisible by 6. The result is compared element-by-element (in both directions)
 * against a plain {@link ListIterator} over the expected multiples of six.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNestedSixes3 {

    /** Source data: the integers 0 through 19. */
    private ArrayList<Integer> list;

    /** Expected result of the composed filters: the multiples of six in {@link #list}. */
    private ArrayList<Integer> sixes;

    /** Matches integers divisible by three. */
    private Predicate<Integer> threePred;

    /** Matches even integers. */
    private Predicate<Integer> evenPred;

    /** Matches every integer (pass-through filter). */
    private Predicate<Integer> truePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        sixes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
            if (i % 6 == 0) {
                sixes.add(i);
            }
        }
        threePred = x -> x % 3 == 0;
        evenPred = x -> x % 2 == 0;
        truePred = x -> true;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        sixes = null;
        threePred = null;
        evenPred = null;
        truePred = null;
    }

    /**
     * Walks {@code testing} against {@code expected} in lockstep, all the way forward,
     * asserting that both iterators report the same indices and elements at every step.
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
     * Walks {@code testing} against {@code expected} in lockstep, all the way backward,
     * asserting that both iterators report the same indices and elements at every step.
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
     * Exhaustively exercises {@code testing} against a fresh {@link ListIterator} over
     * the expected {@code list}: full forward and backward passes, mixed
     * forward/back/forward stepping, a family of partial walks, and finally a long
     * random walk. Every step asserts matching indices and elements.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // walk all the way forward
        walkForward(expected, testing);

        // walk all the way back
        walkBackward(expected, testing);

        // forward, back, forward
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

        // for each i: walk forward i, back i/2, forward i/2, back i
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

        // random walk: take 500 random forward/backward steps, keeping both
        // iterators in sync. walkdescr records the path so a failure can be reproduced.
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

    @Test
    void testNestedSixes3() {
        // (list filtered by "divisible by 3") then "even" then "always true" == multiples of 6
        final FilterListIterator<Integer> divisibleByThreeThenEven =
                new FilterListIterator<>(new FilterListIterator<>(list.listIterator(), threePred), evenPred);
        walkLists(sixes, new FilterListIterator<>(divisibleByThreeThenEven, truePred));
    }
}
