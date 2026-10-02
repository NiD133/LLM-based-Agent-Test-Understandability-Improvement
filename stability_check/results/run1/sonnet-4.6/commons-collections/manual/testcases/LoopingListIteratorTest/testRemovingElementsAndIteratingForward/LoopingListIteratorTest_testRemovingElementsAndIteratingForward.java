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
     * Verifies that elements can be removed one by one while iterating forward through a
     * LoopingListIterator, and that the iterator correctly reflects the shrinking list at each step.
     *
     * <p>Starting with list ["a", "b", "c"], the test advances the iterator, removes the
     * previously returned element, and checks both the list size and iterator availability
     * after each removal. Once the list is empty, hasNext() must return false and next()
     * must throw NoSuchElementException.
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Initial state: list = ["a", "b", "c"], iterator positioned before "a"
        assertTrue(loop.hasNext(), "Iterator should have elements before any iteration");

        // Advance to "a" and then remove it; list becomes ["b", "c"]
        assertEquals("a", loop.next());
        loop.remove();
        assertEquals(2, list.size(), "List should contain 2 elements after removing 'a'");
        assertTrue(loop.hasNext(), "Iterator should still have elements after removing 'a'");

        // Advance to "b" and then remove it; list becomes ["c"]
        assertEquals("b", loop.next());
        loop.remove();
        assertEquals(1, list.size(), "List should contain 1 element after removing 'b'");
        assertTrue(loop.hasNext(), "Iterator should still have elements after removing 'b'");

        // Advance to "c" and then remove it; list becomes []
        assertEquals("c", loop.next());
        loop.remove();
        assertEquals(0, list.size(), "List should be empty after removing all elements");

        // Iterator over an empty list must report no further elements and throw on next()
        assertFalse(loop.hasNext(), "Iterator should report no elements when the list is empty");
        assertThrows(NoSuchElementException.class, () -> loop.next(),
                "next() on an empty looping iterator must throw NoSuchElementException");
    }
}
