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
 * Tests that a FilterListIterator backed by a predicate that rejects every element
 * behaves identically to iterating over an empty list.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFalsePredicate {

    // Source list of integers 0..19 used to build the filtered iterator
    private ArrayList<Integer> list;

    // Predicate that rejects every element, so the filtered iterator is always empty
    private Predicate<Integer> falsePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        falsePred = x -> false;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        falsePred = null;
    }

    // --- Walking helpers ---

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Exhaustively walks {@code testing} in every direction and verifies that it
     * returns exactly the same sequence and indices as an iterator over {@code list}.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Pass 1: full forward sweep
        walkForward(expected, testing);

        // Pass 2: full backward sweep
        walkBackward(expected, testing);

        // Pass 3: alternating next/previous/next for every position
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

        // Pass 4: full backward sweep after the alternating pass
        walkBackward(expected, testing);

        // Pass 5: for each prefix length i, walk forward i, back i/2, forward i/2, back i
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // logic guard
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext()); // logic guard
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Pass 6: random walk — verify index consistency at every step
        final StringBuilder walkdescr = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkdescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkdescr.toString());
                }
            } else {
                walkdescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkdescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkdescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkdescr.toString());
        }
    }

    // --- Test ---

    /**
     * A FilterListIterator whose predicate always returns false must behave like
     * an empty iterator: no elements are visible and index operations match those
     * of an iterator over an empty list.
     */
    @Test
    void testFalsePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), falsePred);
        walkLists(new ArrayList<>(), filtered);
    }
}
