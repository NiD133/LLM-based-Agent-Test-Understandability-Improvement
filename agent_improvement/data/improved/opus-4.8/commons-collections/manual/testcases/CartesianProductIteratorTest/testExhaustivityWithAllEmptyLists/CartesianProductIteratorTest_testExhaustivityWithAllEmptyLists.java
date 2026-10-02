package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link CartesianProductIterator} built entirely from empty
 * lists yields no tuples, since an empty factor collapses the whole product.
 */
public class CartesianProductIteratorTest_testExhaustivityWithAllEmptyLists {

    /**
     * When every input list is empty, the Cartesian product is empty: the
     * iterator must report no elements and {@code next()} must fail.
     */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final List<Character> emptyList = Collections.emptyList();
        final CartesianProductIterator<Character> iterator =
                new CartesianProductIterator<>(emptyList, emptyList, emptyList);

        // Collect every tuple the iterator produces (expected: none).
        final List<Character[]> producedTuples = new ArrayList<>();
        while (iterator.hasNext()) {
            final List<Character> tuple = iterator.next();
            producedTuples.add(tuple.toArray(new Character[0]));
        }

        assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals(0, producedTuples.size());
    }
}
