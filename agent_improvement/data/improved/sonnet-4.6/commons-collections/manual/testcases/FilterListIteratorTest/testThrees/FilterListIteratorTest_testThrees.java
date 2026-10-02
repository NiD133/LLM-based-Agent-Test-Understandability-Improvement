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
 * Tests that {@link FilterListIterator} correctly filters elements divisible by 3,
 * verifying forward/backward traversal and random-walk navigation match a plain list iterator
 * over the expected subset.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testThrees {

    /** Source list of integers 0–19. */
    private ArrayList<Integer> list;

    /** Expected result: integers from {@code list} that are divisible by 3. */
    private ArrayList<Integer> threes;

    /** Predicate that accepts only integers divisible by 3. */
    private Predicate<Integer> threePred;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        threes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
            if (i % 3 == 0) {
                threes.add(Integer.valueOf(i));
            }
        }
        threePred = x -> x % 3 == 0;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        threes = null;
        threePred = null;
    }

    @Test
    void testThrees() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), threePred);
        walkLists(threes, filtered);
    }

    // -----------------------------------------------------------------------
    // Traversal helpers
    // -----------------------------------------------------------------------

    /**
     * Exhaustively walks {@code testing} in both directions and randomly,
     * comparing every step against a fresh {@link ListIterator} over {@code expected}.
     */
    private <E> void walkLists(final List<E> expected, final ListIterator<E> testing) {
        final ListIterator<E> ref = expected.listIterator();

        walkForward(ref, testing);
        walkBackward(ref, testing);

        // forward-back-forward zigzag over every element
        while (ref.hasNext()) {
            assertEquals(ref.nextIndex(), testing.nextIndex());
            assertEquals(ref.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(ref.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(ref.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(ref.next(), testing.next());
        }

        walkBackward(ref, testing);

        // structured walk: forward i steps, back i/2, forward i/2, back i
        for (int i = 0; i < expected.size(); i++) {
            walkSteps(ref, testing, i, true);
            walkSteps(ref, testing, i / 2, false);
            walkSteps(ref, testing, i / 2, true);
            walkSteps(ref, testing, i, false);
        }

        // random walk of 500 steps
        final Random random = new Random();
        final StringBuilder walkTrace = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkTrace.append('+');
                if (ref.hasNext()) {
                    assertEquals(ref.next(), testing.next(), walkTrace.toString());
                }
            } else {
                walkTrace.append('-');
                if (ref.hasPrevious()) {
                    assertEquals(ref.previous(), testing.previous(), walkTrace.toString());
                }
            }
            assertEquals(ref.nextIndex(), testing.nextIndex(), walkTrace.toString());
            assertEquals(ref.previousIndex(), testing.previousIndex(), walkTrace.toString());
        }
    }

    /** Walks {@code steps} steps forward (if {@code forward}) or backward. */
    private void walkSteps(final ListIterator<?> ref, final ListIterator<?> testing,
                           final int steps, final boolean forward) {
        for (int j = 0; j < steps; j++) {
            assertEquals(ref.nextIndex(), testing.nextIndex());
            assertEquals(ref.previousIndex(), testing.previousIndex());
            if (forward) {
                assertTrue(ref.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(ref.next(), testing.next());
            } else {
                assertTrue(ref.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(ref.previous(), testing.previous());
            }
        }
    }

    private void walkForward(final ListIterator<?> ref, final ListIterator<?> testing) {
        while (ref.hasNext()) {
            assertEquals(ref.nextIndex(), testing.nextIndex());
            assertEquals(ref.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(ref.next(), testing.next());
        }
    }

    private void walkBackward(final ListIterator<?> ref, final ListIterator<?> testing) {
        while (ref.hasPrevious()) {
            assertEquals(ref.nextIndex(), testing.nextIndex());
            assertEquals(ref.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(ref.previous(), testing.previous());
        }
    }
}
