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

public class LoopingListIteratorTest_testRemovingElementsAndIteratingForward {

    /**
     * Tests removing elements from the backing list after each forward step.
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> values = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> iterator = new LoopingListIterator<>(values);

        assertTrue(iterator.hasNext());

        // Remove "a"; the iterator should continue from the new first element.
        assertEquals("a", iterator.next());
        iterator.remove();
        assertEquals(2, values.size());
        assertTrue(iterator.hasNext());

        // Remove "b"; only "c" remains available for the next forward step.
        assertEquals("b", iterator.next());
        iterator.remove();
        assertEquals(1, values.size());
        assertTrue(iterator.hasNext());

        // Remove the final element and verify that forward iteration is exhausted.
        assertEquals("c", iterator.next());
        iterator.remove();
        assertEquals(0, values.size());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
