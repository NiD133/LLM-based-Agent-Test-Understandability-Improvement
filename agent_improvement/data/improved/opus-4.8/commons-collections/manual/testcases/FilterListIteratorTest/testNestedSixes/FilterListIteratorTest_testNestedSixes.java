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
 * Verifies that nesting two {@link FilterListIterator}s composes their predicates.
 *
 * <p>Filtering {@code 0..19} first by "multiples of three" and then by "even"
 * must yield exactly the multiples of six. The nested iterator is exercised with
 * the same forward/backward navigation checks as a plain {@link ListIterator}
 * over the expected list of sixes.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNestedSixes {

    /** Upper bound (exclusive) of the source values 0..19. */
    private static final int VALUE_COUNT = 20;

    /** The full source list: 0, 1, 2, ... 19. */
    private ArrayList<Integer> list;

    /** The expected result: every multiple of six within the source list. */
    private ArrayList<Integer> sixes;

    /** Matches values divisible by three. */
    private Predicate<Integer> threePred;

    /** Matches even values. */
    private Predicate<Integer> evenPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        sixes = new ArrayList<>();
        for (int i = 0; i < VALUE_COUNT; i++) {
            list.add(i);
            if (i % 6 == 0) {
                sixes.add(i);
            }
        }
        threePred = x -> x % 3 == 0;
        evenPred = x -> x % 2 == 0;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        sixes = null;
        threePred = null;
        evenPred = null;
    }

    @Test
    void testNestedSixes() {
        // Nest the filters: keep multiples of three, then keep the even ones -> multiples of six.
        final FilterListIterator<Integer> filtered =
            new FilterListIterator<>(new FilterListIterator<>(list.listIterator(), threePred), evenPred);
        walkLists(sixes, filtered);
    }

    /**
     * Drives {@code testing} through the same navigation sequence as a fresh iterator over
     * {@code expectedList}, asserting both stay in lock-step the whole way.
     */
    private <E> void walkLists(final List<E> expectedList, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedList.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // Forward, back, forward at each position.
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        walkBackward(expected, testing);

        // For each i: forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < expectedList.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        // Random walk: step in a random direction 500 times, recording the path for diagnostics.
        final StringBuilder walkDescr = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescr.toString());
                }
            } else {
                walkDescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescr.toString());
        }
    }

    /** Walks both iterators forward until {@code expected} is exhausted. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks both iterators backward until {@code expected} is exhausted. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Advances both iterators forward {@code count} times in lock-step. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // A failure here would mean the test's own bookkeeping is wrong.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Advances both iterators backward {@code count} times in lock-step. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // A failure here would mean the test's own bookkeeping is wrong.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Asserts both iterators report the same next/previous index. */
    private void assertIndexesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
