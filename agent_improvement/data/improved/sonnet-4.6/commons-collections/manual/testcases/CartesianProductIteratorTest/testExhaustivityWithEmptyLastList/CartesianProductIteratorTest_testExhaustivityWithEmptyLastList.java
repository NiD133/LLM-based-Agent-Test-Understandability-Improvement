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

/**
 * Tests that a CartesianProductIterator over an input set that includes an empty list
 * produces zero tuples, because an empty factor makes the whole Cartesian product empty.
 */
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
     * When the last input list is empty, the iterator must yield no tuples
     * and throw NoSuchElementException on any subsequent call to next().
     */
    @Test
    void testExhaustivityWithEmptyLastList() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, numbers, emptyList);
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }
        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }
}
