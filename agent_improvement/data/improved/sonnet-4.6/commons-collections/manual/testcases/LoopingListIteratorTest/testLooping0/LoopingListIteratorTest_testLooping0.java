package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping0 {

    /**
     * Tests that an empty LoopingListIterator reports no elements and throws
     * NoSuchElementException for both next() and previous() traversal directions.
     */
    @Test
    void testLooping0() {
        final List<Object> list = new ArrayList<>();
        final LoopingListIterator<Object> loop = new LoopingListIterator<>(list);

        assertFalse(loop.hasNext(),     "empty iterator should have no next element");
        assertFalse(loop.hasPrevious(), "empty iterator should have no previous element");
        assertThrows(NoSuchElementException.class, loop::next,
                "next() on empty iterator must throw NoSuchElementException");
        assertThrows(NoSuchElementException.class, loop::previous,
                "previous() on empty iterator must throw NoSuchElementException");
    }
}
