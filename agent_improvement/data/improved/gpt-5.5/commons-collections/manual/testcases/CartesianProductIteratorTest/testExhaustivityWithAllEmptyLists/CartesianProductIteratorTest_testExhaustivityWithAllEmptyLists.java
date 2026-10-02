package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartesianProductIteratorTest_testExhaustivityWithAllEmptyLists {

    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        emptyList = Collections.emptyList();
    }

    /**
     * Tests that no tuples are returned when all input lists are empty.
     */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(emptyList, emptyList, emptyList);

        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }
}
