package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LoopingListIterator#set(Object)} replaces the element
 * that was last returned by {@code next()} or {@code previous()}.
 *
 * <p>The notation in the comments shows the list contents with angle brackets
 * marking the element the iterator's internal cursor most recently crossed
 * (i.e. the element a following {@code set} would replace).</p>
 */
public class LoopingListIteratorTest_testSet {

    @Test
    void testSet() {
        // Starting list: [q, r, z]. The cursor begins before the first element.
        final List<String> list = Arrays.asList("q", "r", "z");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // previous() from the start loops around to the last element, "z".
        // List view:  q  r <z>
        assertEquals("z", loop.previous());

        // Replace the last-returned element ("z") with "c".
        // List view:  q  r <c>   ->  [q, r, c]
        loop.set("c");

        // reset() moves the cursor back to the beginning of the list.
        loop.reset();

        // next() returns the first element, "q".
        // List view: <q> r  c
        assertEquals("q", loop.next());

        // Replace the last-returned element ("q") with "a".
        // List view: <a> r  c   ->  [a, r, c]
        loop.set("a");

        // next() returns the second element, "r".
        // List view:  a <r> c
        assertEquals("r", loop.next());

        // Replace the last-returned element ("r") with "b".
        // List view:  a <b> c   ->  [a, b, c]
        loop.set("b");

        // reset() again, then verify the list now reads [a, b, c].
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
    }
}
