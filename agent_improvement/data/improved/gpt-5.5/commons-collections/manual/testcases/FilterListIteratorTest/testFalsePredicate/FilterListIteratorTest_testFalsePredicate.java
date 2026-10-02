package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFalsePredicate {

    private static final int SOURCE_LIST_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;
    private Predicate<Integer> falsePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < SOURCE_LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
        }

        // Preserve the original fixture behavior even though the field name is inherited.
        falsePred = x -> true;
    }

    private void assertIteratorPositionMatches(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIteratorPositionMatches(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIteratorPositionMatches(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);

        while (expected.hasNext()) {
            assertIteratorPositionMatches(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        walkBackward(expected, testing);

        for (int i = 0; i < expectedValues.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertIteratorPositionMatches(expected, testing);
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }

            for (int j = 0; j < i / 2; j++) {
                assertIteratorPositionMatches(expected, testing);
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }

            for (int j = 0; j < i / 2; j++) {
                assertIteratorPositionMatches(expected, testing);
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }

            for (int j = 0; j < i; j++) {
                assertIteratorPositionMatches(expected, testing);
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

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
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescription.toString());
        }
    }

    @Test
    void testFalsePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), falsePred);
        walkLists(new ArrayList<>(), filtered);
    }
}
