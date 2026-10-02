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
 * Tests that {@link FilterListIterator} correctly exposes only even-numbered
 * elements when constructed with an even-number predicate, and that bidirectional
 * traversal (forward, backward, and random walks) matches a plain list iterator
 * over the pre-computed even-number list.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testEvens {

    /** Source list containing integers 0–19. */
    private ArrayList<Integer> list;

    /** Expected results: integers from {@code list} that satisfy {@code evenPred}. */
    private ArrayList<Integer> evens;

    /** Accepts only even integers. */
    private Predicate<Integer> evenPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list  = new ArrayList<>();
        evens = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(i);
            if (i % 2 == 0) {
                evens.add(i);
            }
        }

        evenPred = x -> x % 2 == 0;
    }

    @AfterEach
    public void tearDown() {
        list     = null;
        evens    = null;
        evenPred = null;
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), evenPred);
        walkLists(evens, filtered);
    }

    // -----------------------------------------------------------------------
    // Traversal helpers
    // -----------------------------------------------------------------------

    /**
     * Exhaustively exercises bidirectional traversal of {@code testing} by
     * comparing every step against a plain {@link ListIterator} over {@code list}.
     *
     * <p>The walk proceeds in five phases:
     * <ol>
     *   <li>All the way forward.</li>
     *   <li>All the way backward.</li>
     *   <li>Forward–backward–forward zigzag across the whole list.</li>
     *   <li>Nested forward/back sweeps of increasing depth.</li>
     *   <li>500-step random walk.</li>
     * </ol>
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: full forward pass
        walkForward(expected, testing);

        // Phase 2: full backward pass
        walkBackward(expected, testing);

        // Phase 3: forward–back–forward zigzag
        while (expected.hasNext()) {
            assertIndexSync(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());

            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());

            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        // Phase 4: nested sweeps — walk forward i steps, back i/2, forward i/2, back i
        walkBackward(expected, testing);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertIndexSync(expected, testing);
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertIndexSync(expected, testing);
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertIndexSync(expected, testing);
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertIndexSync(expected, testing);
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Phase 5: 500-step random walk ('+' = forward, '-' = backward)
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
            assertEquals(expected.nextIndex(),     testing.nextIndex(),     walkDescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescr.toString());
        }
    }

    /** Asserts that {@code testing} reports the same cursor position as {@code expected}. */
    private void assertIndexSync(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(),     testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    /** Advances both iterators to the end, asserting element and index equality at each step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndexSync(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Retreats both iterators to the start, asserting element and index equality at each step. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndexSync(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }
}
