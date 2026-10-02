package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LoopingListIterator#add(Object)}.
 *
 * <p>The iterator wraps a mutable list and loops endlessly around it. The
 * comments below track the iterator's cursor with the notation
 * {@code a <b> e f}, where the element in angle brackets is the one that a
 * call to {@code next()} would return; a call to {@code previous()} would
 * return the element immediately to its left. Inserting with {@code add(x)}
 * places {@code x} just before that bracketed element, so the new element
 * becomes the value {@code previous()} would return.</p>
 */
public class LoopingListIteratorTest_testAdd {

    @Test
    void testAdd() {
        // ------------------------------------------------------------------
        // Scenario 1: insert while walking forward with next().
        // ------------------------------------------------------------------
        List<String> list = new ArrayList<>(Arrays.asList("b", "e", "f"));
        LoopingListIterator<String> loop = new LoopingListIterator<>(list); // <b> e f

        loop.add("a");                          // a <b> e f
        assertEquals("b", loop.next());         // a b <e> f

        loop.reset();                           // <a> b e f
        assertEquals("a", loop.next());         // a <b> e f
        assertEquals("b", loop.next());         // a b <e> f

        loop.add("c");                          // a b c <e> f
        assertEquals("e", loop.next());         // a b c e <f>
        assertEquals("e", loop.previous());     // a b c <e> f
        assertEquals("c", loop.previous());     // a b <c> e f
        assertEquals("c", loop.next());         // a b c <e> f

        loop.add("d");                          // a b c d <e> f

        // A full lap from the start confirms the final order: a b c d e f.
        loop.reset();                           // <a> b c d e f
        assertEquals("a", loop.next());         // a <b> c d e f
        assertEquals("b", loop.next());         // a b <c> d e f
        assertEquals("c", loop.next());         // a b c <d> e f
        assertEquals("d", loop.next());         // a b c d <e> f
        assertEquals("e", loop.next());         // a b c d e <f>
        assertEquals("f", loop.next());         // loops back to <a> b c d e f
        assertEquals("a", loop.next());         // a <b> c d e f

        // ------------------------------------------------------------------
        // Scenario 2: insert while walking backward with previous().
        // ------------------------------------------------------------------
        list = new ArrayList<>(Arrays.asList("b", "e", "f"));
        loop = new LoopingListIterator<>(list); // <b> e f

        loop.add("a");                          // a <b> e f
        assertEquals("a", loop.previous());     // <a> b e f

        loop.reset();                           // <a> b e f
        assertEquals("f", loop.previous());     // loops back to a b e <f>
        assertEquals("e", loop.previous());     // a b <e> f

        loop.add("d");                          // a b d <e> f
        assertEquals("d", loop.previous());     // a b <d> e f

        loop.add("c");                          // a b c <d> e f
        assertEquals("c", loop.previous());     // a b <c> d e f

        // A full lap from the start confirms the final order: a b c d e f.
        loop.reset();                           // <a> b c d e f
        assertEquals("a", loop.next());         // a <b> c d e f
        assertEquals("b", loop.next());         // a b <c> d e f
        assertEquals("c", loop.next());         // a b c <d> e f
        assertEquals("d", loop.next());         // a b c d <e> f
        assertEquals("e", loop.next());         // a b c d e <f>
        assertEquals("f", loop.next());         // loops back to <a> b c d e f
        assertEquals("a", loop.next());         // a <b> c d e f
    }
}
