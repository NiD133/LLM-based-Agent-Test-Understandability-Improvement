package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFailingHasNextBug {

    private List<Integer> source;
    private List<Integer> multiplesOfFour;
    private Predicate<Integer> multipleOfFourPredicate;

    @BeforeEach
    public void setUp() {
        source = new ArrayList<>();
        multiplesOfFour = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            source.add(Integer.valueOf(i));
            if (i % 4 == 0) {
                multiplesOfFour.add(Integer.valueOf(i));
            }
        }

        multipleOfFourPredicate = x -> x % 4 == 0;
    }

    @Test
    void testFailingHasNextBug() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(source.listIterator(), multipleOfFourPredicate);
        final ListIterator<Integer> expected = multiplesOfFour.listIterator();

        while (expected.hasNext()) {
            expected.next();
            filtered.next();
        }

        assertTrue(filtered.hasPrevious());
        assertFalse(filtered.hasNext());
        assertEquals(expected.previous(), filtered.previous());
    }
}
