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

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFours {

    private static final int LIST_SIZE = 20;

    private ArrayList<Integer> list;
    private ArrayList<Integer> fours;
    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        fours = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
            if (i % 4 == 0) {
                fours.add(Integer.valueOf(i));
            }
        }
        fourPred = x -> x % 4 == 0;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        fours = null;
        fourPred = null;
    }

    @Test
    void testFours() {
        // FilterListIterator should yield only multiples of 4 from the full list
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), fourPred);
        walkLists(fours, filtered);
    }

    /**
     * Verifies that {@code testing} traverses exactly the same elements as a plain
     * list iterator over {@code list}, exercising multiple forward/backward movement
     * patterns to ensure correct element values and index tracking throughout.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: full forward traversal
        walkForward(expected, testing);

        // Phase 2: full backward traversal
        walkBackward(expected, testing);

        // Phase 3: zigzag — advance one, retreat one, advance one for each position
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

        // Phase 4: full backward traversal to reset to the start
        walkBackward(expected, testing);

        // Phase 5: for each stride length, walk forward by stride, back by stride/2,
        // forward by stride/2, then back by stride — net movement is always zero
        for (int stride = 0; stride < list.size(); stride++) {
            for (int step = 0; step < stride; step++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int step = 0; step < stride / 2; step++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int step = 0; step < stride / 2; step++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int step = 0; step < stride; step++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Phase 6: random walk — the accumulated step history is included in failure
        // messages to make it possible to reproduce a failing sequence
        final StringBuilder walkHistory = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkHistory.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkHistory.toString());
                }
            } else {
                walkHistory.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkHistory.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkHistory.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkHistory.toString());
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

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }
}
