package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testSet {

    /**
     * Verifies that set() replaces the element last returned by next() or previous(),
     * and that all replacements are visible when the list is traversed afterwards.
     *
     * Three replacements are performed:
     *   1. Replace the last element via previous() (which wraps around from the start).
     *   2. Replace the first element via next().
     *   3. Replace the middle element via next().
     * A final traversal confirms the list reads [a, b, c] after all replacements.
     */
    @Test
    void testSet() {
        // List starts as: ["q", "r", "z"]; iterator cursor is before index 0.
        final List<String> list = Arrays.asList("q", "r", "z");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // --- Phase 1: replace last element using previous() ---
        // previous() wraps around from the start and returns the last element "z".
        assertEquals("z", loop.previous()); // cursor is now after index 2
        loop.set("c");                       // replaces "z" → list: ["q", "r", "c"]

        // --- Phase 2: replace remaining elements using next() ---
        loop.reset();                        // cursor back to before index 0
        assertEquals("q", loop.next());     // returns index 0; cursor is now after index 0
        loop.set("a");                       // replaces "q" → list: ["a", "r", "c"]

        assertEquals("r", loop.next());     // returns index 1; cursor is now after index 1
        loop.set("b");                       // replaces "r" → list: ["a", "b", "c"]

        // --- Phase 3: verify the final list state after all replacements ---
        loop.reset();
        assertEquals("a", loop.next()); // first element
        assertEquals("b", loop.next()); // second element
        assertEquals("c", loop.next()); // third element (replaced via previous())
    }
}
