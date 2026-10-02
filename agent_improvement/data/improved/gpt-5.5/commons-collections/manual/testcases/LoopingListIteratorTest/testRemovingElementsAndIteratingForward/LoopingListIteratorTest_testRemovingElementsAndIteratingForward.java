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
     * Tests removing each element immediately after it is returned while iterating forward.
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> iterator = new LoopingListIterator<>(list);

        assertTrue(iterator.hasNext());

        assertEquals("a", iterator.next());
        iterator.remove();
        assertEquals(2, list.size());

        assertTrue(iterator.hasNext());

        assertEquals("b", iterator.next());
        iterator.remove();
        assertEquals(1, list.size());

        assertTrue(iterator.hasNext());

        assertEquals("c", iterator.next());
        iterator.remove();
        assertEquals(0, list.size());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
