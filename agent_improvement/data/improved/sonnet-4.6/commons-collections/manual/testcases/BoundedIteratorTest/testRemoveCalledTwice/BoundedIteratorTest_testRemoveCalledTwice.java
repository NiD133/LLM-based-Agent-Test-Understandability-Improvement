package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveCalledTwice {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Verifies that calling {@code remove()} a second time without an intervening
     * {@code next()} call throws {@link IllegalStateException}.
     * <p>
     * Setup: a BoundedIterator with offset=1 and max=5 over testList, so the
     * first element returned by {@code next()} is "b". After one successful
     * {@code remove()}, a second {@code remove()} must be rejected.
     */
    @Test
    void testRemoveCalledTwice() {
        // Use a mutable copy so remove() can modify the underlying list.
        final List<String> mutableList = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(mutableList.iterator(), 1, 5);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());

        // First remove is valid — advances past the current element.
        iter.remove();

        // Second remove without a next() call must throw.
        assertThrows(IllegalStateException.class, () -> iter.remove());
    }
}
