package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet#equals(Object)}.
 *
 * <p>Two CharSets are equal only when they are defined the same way. In
 * particular the listed set "abc", the range "a-c" and the negated range
 * "^a-c" all represent different definitions and are therefore never equal
 * to each other, even though some describe overlapping characters.</p>
 */
public class CharSetTest_testEquals_Object extends AbstractLangTest {

    @Test
    void testEquals_Object() {
        // Listed characters: "abc"
        final CharSet listedAbc = CharSet.getInstance("abc");
        final CharSet listedAbcCopy = CharSet.getInstance("abc");

        // Range: "a-c"
        final CharSet rangeAtoC = CharSet.getInstance("a-c");
        final CharSet rangeAtoCCopy = CharSet.getInstance("a-c");

        // Negated range: "^a-c"
        final CharSet negatedAtoC = CharSet.getInstance("^a-c");
        final CharSet negatedAtoCCopy = CharSet.getInstance("^a-c");

        // A CharSet is never equal to null.
        assertNotEquals(null, listedAbc);

        // "abc": equal to itself and to an identically defined copy,
        // but not to the range or negated-range definitions.
        assertEquals(listedAbc, listedAbc);
        assertEquals(listedAbc, listedAbcCopy);
        assertNotEquals(listedAbc, rangeAtoC);
        assertNotEquals(listedAbc, negatedAtoC);

        // "a-c": equal to itself and to an identically defined copy,
        // but not to the listed or negated-range definitions.
        assertNotEquals(rangeAtoC, listedAbc);
        assertEquals(rangeAtoC, rangeAtoC);
        assertEquals(rangeAtoC, rangeAtoCCopy);
        assertNotEquals(rangeAtoC, negatedAtoC);

        // "^a-c": equal to itself and to an identically defined copy,
        // but not to the listed or plain-range definitions.
        assertNotEquals(negatedAtoC, listedAbc);
        assertNotEquals(negatedAtoC, rangeAtoC);
        assertEquals(negatedAtoC, negatedAtoC);
        assertEquals(negatedAtoC, negatedAtoCCopy);
    }
}
