package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testRemovingElementsAndIteratingBackwards {

    /**
     * Verifies that elements can be removed one by one while iterating backwards
     * through a LoopingListIterator, and that the iterator correctly detects when
     * the list becomes empty.
     *
     * <p>The LoopingListIterator starts at index 0 (the beginning of the list).
     * Calling {@code previous()} when at position 0 wraps around to the last element.
     * After each {@code previous()} call, the returned element is removed via
     * {@code remove()}, shrinking the list by one. Once all elements are removed,
     * {@code hasPrevious()} must return {@code false} and {@code previous()} must
     * throw {@link NoSuchElementException}.
     *
     * <p>Initial list: ["a", "b", "c"]
     * <ol>
     *   <li>previous() wraps to end → returns "c"; remove "c" → list is ["a", "b"]</li>
     *   <li>previous() → returns "b"; remove "b" → list is ["a"]</li>
     *   <li>previous() → returns "a"; remove "a" → list is []</li>
     * </ol>
     */
    @Test
    void testRemovingElementsAndIteratingBackwards() {
        // Set up: list with three elements; iterator positioned at the start (index 0)
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Even though the iterator is at position 0, hasPrevious() returns true
        // because the list is non-empty — previous() will wrap around to the last element.
        assertTrue(loop.hasPrevious());

        // Step 1: previous() wraps from index 0 to the last element "c"; then remove it.
        assertEquals("c", loop.previous());
        loop.remove();
        assertEquals(2, list.size()); // list is now ["a", "b"]

        // Step 2: previous() returns "b" (now the last element after wrapping); then remove it.
        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
        loop.remove();
        assertEquals(1, list.size()); // list is now ["a"]

        // Step 3: previous() returns "a" (the only remaining element); then remove it.
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        loop.remove();
        assertEquals(0, list.size()); // list is now empty

        // With the list empty, hasPrevious() must report false and previous() must throw.
        assertFalse(loop.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> loop.previous());
    }
}
