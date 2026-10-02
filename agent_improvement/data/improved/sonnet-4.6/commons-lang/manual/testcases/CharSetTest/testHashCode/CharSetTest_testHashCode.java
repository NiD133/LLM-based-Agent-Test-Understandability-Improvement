package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharSetTest_testHashCode extends AbstractLangTest {

    @Test
    void testHashCode() {
        // Three distinct CharSet representations: individual chars, a range, and a negated range
        final CharSet abc    = CharSet.getInstance("abc");
        final CharSet abc2   = CharSet.getInstance("abc");
        final CharSet atoc   = CharSet.getInstance("a-c");
        final CharSet atoc2  = CharSet.getInstance("a-c");
        final CharSet notatoc  = CharSet.getInstance("^a-c");
        final CharSet notatoc2 = CharSet.getInstance("^a-c");

        // hashCode must be self-consistent (same object, same call)
        assertEquals(abc.hashCode(),    abc.hashCode(),    "hashCode of 'abc' must be stable across calls");
        assertEquals(atoc.hashCode(),   atoc.hashCode(),   "hashCode of 'a-c' must be stable across calls");
        assertEquals(notatoc.hashCode(), notatoc.hashCode(), "hashCode of '^a-c' must be stable across calls");

        // Equal CharSet instances built from the same descriptor must share the same hashCode
        assertEquals(abc.hashCode(),    abc2.hashCode(),    "Two CharSets built from 'abc' must have equal hashCodes");
        assertEquals(atoc.hashCode(),   atoc2.hashCode(),   "Two CharSets built from 'a-c' must have equal hashCodes");
        assertEquals(notatoc.hashCode(), notatoc2.hashCode(), "Two CharSets built from '^a-c' must have equal hashCodes");
    }
}
