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

public class LoopingListIteratorTest_testLooping1 {

    /**
     * Tests that a LoopingListIterator on a single-element list loops
     * indefinitely in both forward and backward directions.
     *
     * With only one element "a", every call to next() or previous() must
     * return "a", and hasNext() / hasPrevious() must always return true
     * because the iterator wraps around the list continuously.
     */
    @Test
    void testLooping1() {
        final List<String> list = Arrays.asList("a");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Forward iteration: the single element "a" is returned on every next()
        // call, with hasNext() always true because the iterator wraps around.
        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        // Backward iteration: the single element "a" is returned on every previous()
        // call, with hasPrevious() always true because the iterator wraps around.
        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
    }
}
