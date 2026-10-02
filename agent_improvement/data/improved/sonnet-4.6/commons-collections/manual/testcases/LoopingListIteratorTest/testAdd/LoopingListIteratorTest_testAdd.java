package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LoopingListIterator#add(Object)}.
 *
 * The cursor notation used in comments: angle brackets mark where the iterator
 * cursor sits between elements, e.g. {@code a <b> c} means the cursor is
 * between 'a' and 'b' (next() returns 'b', previous() returns 'a').
 */
public class LoopingListIteratorTest_testAdd {

    /**
     * Verifies that elements added via {@code add()} are positioned correctly
     * when the list is traversed forward with {@code next()}.
     *
     * Starting list: [b, e, f]
     *
     * Inserts 'a' at the front, then 'c' after 'b', then 'd' after 'c',
     * ultimately producing [a, b, c, d, e, f], and confirms that iterating
     * forward visits all six elements in order, looping back to 'a' after 'f'.
     */
    @Test
    void testAdd_forwardTraversal() {
        List<String> list = new ArrayList<>(Arrays.asList("b", "e", "f"));
        LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Insert 'a' before 'b'; cursor is now between 'a' and 'b': a <> b e f
        loop.add("a");

        // next() should return 'b' (the element right after the cursor)
        assertEquals("b", loop.next()); // a b <> e f

        // reset() puts the cursor back before all elements: <> a b e f
        loop.reset();

        // Walk forward to verify the list contents after adding 'a'
        assertEquals("a", loop.next()); // a <> b e f
        assertEquals("b", loop.next()); // a b <> e f

        // Insert 'c' after 'b'; cursor is now between 'c' and 'e': a b c <> e f
        loop.add("c");

        // Verify forward and backward navigation around 'c'
        assertEquals("e", loop.next());    // a b c e <> f
        assertEquals("e", loop.previous()); // a b c <> e f
        assertEquals("c", loop.previous()); // a b <> c e f
        assertEquals("c", loop.next());    // a b c <> e f

        // Insert 'd' after 'c'; cursor is now between 'd' and 'e': a b c d <> e f
        loop.add("d");

        // reset() and walk the full list — looping from 'f' back to 'a'
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
        assertEquals("d", loop.next());
        assertEquals("e", loop.next());
        assertEquals("f", loop.next()); // wraps back to start on next call
        assertEquals("a", loop.next()); // looping behaviour confirmed
    }

    /**
     * Verifies that elements added via {@code add()} are positioned correctly
     * when the list is traversed backward with {@code previous()}.
     *
     * Starting list: [b, e, f]
     *
     * Inserts 'a' at the front, then 'd' before 'e', then 'c' before 'd',
     * ultimately producing [a, b, c, d, e, f], and confirms that iterating
     * forward visits all six elements in order, looping back to 'a' after 'f'.
     */
    @Test
    void testAdd_backwardTraversal() {
        List<String> list = new ArrayList<>(Arrays.asList("b", "e", "f"));
        LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        // Insert 'a' before 'b'; cursor is now between 'a' and 'b': a <> b e f
        loop.add("a");

        // previous() wraps around and returns 'a' (the last element visited going backwards)
        assertEquals("a", loop.previous()); // <> a b e f

        // reset() puts the cursor back before all elements: <> a b e f
        loop.reset();

        // previous() from the start wraps to the end of the list
        assertEquals("f", loop.previous()); // a b e <> f
        assertEquals("e", loop.previous()); // a b <> e f

        // Insert 'd' after 'e' going backwards (before 'e' in forward terms):
        // cursor is now between 'd' and 'e': a b d <> e f
        loop.add("d");

        assertEquals("d", loop.previous()); // a b <> d e f

        // Insert 'c' after 'd' going backwards (before 'd' in forward terms):
        // cursor is now between 'c' and 'd': a b c <> d e f
        loop.add("c");

        assertEquals("c", loop.previous()); // a b <> c d e f

        // reset() and walk the full list forward — confirms final order [a, b, c, d, e, f]
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
        assertEquals("d", loop.next());
        assertEquals("e", loop.next());
        assertEquals("f", loop.next()); // wraps back to start on next call
        assertEquals("a", loop.next()); // looping behaviour confirmed
    }
}
