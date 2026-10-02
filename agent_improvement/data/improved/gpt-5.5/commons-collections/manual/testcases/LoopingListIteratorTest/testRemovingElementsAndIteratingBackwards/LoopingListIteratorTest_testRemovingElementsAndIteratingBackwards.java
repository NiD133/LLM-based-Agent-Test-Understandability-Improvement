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
     * Tests removing elements while repeatedly moving backwards through a
     * wrapped ArrayList.
     */
    @Test
    void testRemovingElementsAndIteratingBackwards() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasPrevious());
        assertEquals("c", loop.previous());
        loop.remove();
        assertEquals(2, list.size());

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
        loop.remove();
        assertEquals(1, list.size());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        loop.remove();
        assertEquals(0, list.size());

        assertFalse(loop.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> loop.previous());
    }
}
