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
 * Tests that a {@link FilterListIterator} backed by an always-true predicate
 * behaves identically to an unfiltered {@link ListIterator}: every element
 * must be visible, and navigation (forward, backward, mixed, random) must
 * produce the same elements and indices as the plain list iterator.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testTruePredicate {

    /** Source list containing integers 0–19, built once per test. */
    private ArrayList<Integer> list;

    /** Predicate that unconditionally accepts every element. */
    private Predicate<Integer> truePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        truePred = x -> true;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        truePred = null;
    }

    /**
     * A {@link FilterListIterator} with an always-true predicate must let every
     * element through. This test exhaustively compares it against a plain list
     * iterator under forward, backward, alternating, zigzag, and random walks.
     */
    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }

    // -------------------------------------------------------------------------
    // Navigation helpers
    // -------------------------------------------------------------------------

    /** Steps both iterators forward together, asserting equal elements and indices. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Steps both iterators backward together, asserting equal elements and indices. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Validates {@code testing} against a fresh list iterator for {@code list}
     * using five navigation patterns in sequence:
     * <ol>
     *   <li><b>Full forward pass</b> — walk every element from start to end.</li>
     *   <li><b>Full backward pass</b> — walk every element from end to start.</li>
     *   <li><b>Alternating next/previous</b> — advance one, retreat one, advance one, repeat.</li>
     *   <li><b>Zigzag of increasing depth</b> — for each depth {@code i}: advance {@code i},
     *       retreat {@code i/2}, advance {@code i/2}, retreat {@code i}.</li>
     *   <li><b>500-step random walk</b> — randomly step forward or backward;
     *       the walk sequence is recorded for failure diagnostics.</li>
     * </ol>
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: full forward pass
        walkForward(expected, testing);

        // Phase 2: full backward pass
        walkBackward(expected, testing);

        // Phase 3: alternating next/previous/next across the whole list
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

        // Phase 4: zigzag of increasing depth — reset to start first
        walkBackward(expected, testing);
        for (int i = 0; i < list.size(); i++) {
            // advance i steps
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // logic guard: test is mis-counting if this fails
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            // retreat i/2 steps
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            // re-advance i/2 steps
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // logic guard
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            // retreat i steps back to the start of this depth's window
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Phase 5: 500-step random walk; record the step sequence for failure messages
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescription.append('+');
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append('-');
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescription.toString());
        }
    }
}
