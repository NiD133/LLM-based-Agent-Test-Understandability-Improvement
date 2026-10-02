package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testLooping0 {

    /**
     * Verifies how a {@link LoopingListIterator} behaves when it wraps an empty
     * list. With no elements to loop over, the iterator should report that there
     * is nothing to traverse in either direction, and any attempt to move should
     * fail with a {@link NoSuchElementException}.
     */
    @Test
    void emptyListHasNoElementsToLoopOver() {
        // Arrange: a looping iterator over an empty list.
        final List<Object> emptyList = new ArrayList<>();
        final LoopingListIterator<Object> loopingIterator = new LoopingListIterator<>(emptyList);

        // Assert: there is nothing to iterate over in either direction.
        assertFalse(loopingIterator.hasNext(), "empty list should report no next element");
        assertFalse(loopingIterator.hasPrevious(), "empty list should report no previous element");

        // Assert: advancing in either direction throws because the list is empty.
        assertThrows(NoSuchElementException.class, () -> loopingIterator.next(),
                "next() on an empty list should throw");
        assertThrows(NoSuchElementException.class, () -> loopingIterator.previous(),
                "previous() on an empty list should throw");
    }
}
