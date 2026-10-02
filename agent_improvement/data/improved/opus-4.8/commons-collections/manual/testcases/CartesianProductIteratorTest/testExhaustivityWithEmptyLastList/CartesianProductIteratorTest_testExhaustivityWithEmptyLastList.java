package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testExhaustivityWithEmptyLastList {

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        emptyList = Collections.emptyList();
    }

    /**
     * When the last input list is empty, the Cartesian product is empty:
     * the iterator yields no tuples and {@code next()} throws
     * {@link NoSuchElementException}.
     */
    @Test
    void testExhaustivityWithEmptyLastList() {
        final List<Character[]> tuples = new ArrayList<>();
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, numbers, emptyList);

        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            tuples.add(tuple.toArray(new Character[0]));
        }

        assertEquals(0, tuples.size(), "no tuples expected when an input list is empty");
        assertThrows(NoSuchElementException.class, it::next);
    }
}
