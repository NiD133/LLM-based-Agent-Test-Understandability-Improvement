package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet#contains(char)} for the different ways a set of
 * characters can be described.
 */
public class CharSetTest_testContains_Char extends AbstractLangTest {

    @Test
    void testContains_Char() {
        // "b-d": the range b..d, so it contains b, c and d but nothing outside.
        final CharSet rangeBtoD = CharSet.getInstance("b-d");
        assertContainsExactly_BtoD(rangeBtoD);

        // "bcd": the same three characters listed individually -> same membership.
        final CharSet listedBcd = CharSet.getInstance("bcd");
        assertContainsExactly_BtoD(listedBcd);

        // "bd": only the two characters b and d (c is NOT included).
        final CharSet listedBandD = CharSet.getInstance("bd");
        assertFalse(listedBandD.contains('a'));
        assertTrue(listedBandD.contains('b'));
        assertFalse(listedBandD.contains('c'));
        assertTrue(listedBandD.contains('d'));
        assertFalse(listedBandD.contains('e'));

        // "^b-d": the negation of b..d, so it contains everything except b, c and d.
        final CharSet negatedBtoD = CharSet.getInstance("^b-d");
        assertTrue(negatedBtoD.contains('a'));
        assertFalse(negatedBtoD.contains('b'));
        assertFalse(negatedBtoD.contains('c'));
        assertFalse(negatedBtoD.contains('d'));
        assertTrue(negatedBtoD.contains('e'));

        // "d-b": a reversed range, which CharSet normalises to the same range as "b-d".
        final CharSet reversedDtoB = CharSet.getInstance("d-b");
        assertContainsExactly_BtoD(reversedDtoB);
        // The reversed range is stored as the single, normalised range "b-d".
        final Set<CharRange> ranges = reversedDtoB.getCharRanges();
        assertEquals("[b-d]", reversedDtoB.toString());
        assertEquals(1, ranges.size());
    }

    /**
     * Asserts that the given set contains exactly the characters b, c and d,
     * i.e. it covers the range b..d and excludes the immediate neighbours a and e.
     */
    private static void assertContainsExactly_BtoD(final CharSet charSet) {
        assertFalse(charSet.contains('a'));
        assertTrue(charSet.contains('b'));
        assertTrue(charSet.contains('c'));
        assertTrue(charSet.contains('d'));
        assertFalse(charSet.contains('e'));
    }
}
