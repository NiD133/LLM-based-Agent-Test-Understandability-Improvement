package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping0 {

    /**
     * An iterator over an empty list has no next or previous element, and both
     * traversal directions fail with the documented exception.
     */
    @Test
    void testLooping0() {
        final List<Object> emptyList = new ArrayList<>();
        final LoopingListIterator<Object> iterator = new LoopingListIterator<>(emptyList);

        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThrows(NoSuchElementException.class, () -> iterator.previous());
    }
}
