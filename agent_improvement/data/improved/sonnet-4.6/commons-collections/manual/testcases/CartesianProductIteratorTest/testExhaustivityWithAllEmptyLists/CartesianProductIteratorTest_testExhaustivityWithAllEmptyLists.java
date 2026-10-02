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
     * When all input lists are empty, the Cartesian product is empty: no tuples
     * should be produced, and calling next() on the exhausted iterator must throw
     * NoSuchElementException.
     */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(emptyList, emptyList, emptyList);

        final List<Character[]> resultsList = new ArrayList<>();
        while (it.hasNext()) {
            resultsList.add(it.next().toArray(new Character[0]));
        }

        assertEquals(0, resultsList.size(), "No tuples expected when all input lists are empty");
        assertThrows(NoSuchElementException.class, it::next,
                "next() on an exhausted iterator must throw NoSuchElementException");
    }
}
