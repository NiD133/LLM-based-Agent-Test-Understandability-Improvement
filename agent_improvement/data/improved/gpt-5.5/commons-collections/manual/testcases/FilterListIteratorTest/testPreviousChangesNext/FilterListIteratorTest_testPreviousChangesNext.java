package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterListIteratorTest_testPreviousChangesNext {

    private List<Integer> list;
    private List<Integer> threes;

    private Predicate<Integer> truePredicate;
    private Predicate<Integer> threePredicate;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        threes = new ArrayList<>();

        for (int value = 0; value < 20; value++) {
            list.add(Integer.valueOf(value));
            if (value % 3 == 0) {
                threes.add(Integer.valueOf(value));
            }
        }

        truePredicate = value -> true;
        threePredicate = value -> value % 3 == 0;
    }

    private void assertPreviousRefreshesNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());

        final Object expectedPrevious = expected.previous();
        final Object testingPrevious = testing.previous();
        assertEquals(expectedPrevious, testingPrevious);

        final Object expectedNext = expected.next();
        final Object testingNext = testing.next();
        assertEquals(expectedPrevious, testingNext);
        assertEquals(expectedPrevious, expectedNext);
        assertEquals(testingPrevious, testingNext);
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    @Test
    void testPreviousChangesNext() {
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePredicate);
            final ListIterator<Integer> expected = threes.listIterator();
            walkForward(expected, filtered);
            assertPreviousRefreshesNext(expected, filtered);
        }
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePredicate);
            final ListIterator<Integer> expected = list.listIterator();
            walkForward(expected, filtered);
            assertPreviousRefreshesNext(expected, filtered);
        }
    }
}
