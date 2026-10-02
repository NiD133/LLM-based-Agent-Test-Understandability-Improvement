package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testContains_Char extends AbstractLangTest {

    // "b-d" defines a range covering 'b', 'c', and 'd'.
    private final CharSet rangeB_to_D = CharSet.getInstance("b-d");

    // "bcd" lists individual characters; should include the same chars as "b-d".
    private final CharSet individualBCD = CharSet.getInstance("bcd");

    // "bd" lists only 'b' and 'd', no 'c' in between.
    private final CharSet individualBD = CharSet.getInstance("bd");

    // "^b-d" negates the range, so it matches everything EXCEPT 'b', 'c', 'd'.
    private final CharSet negatedB_to_D = CharSet.getInstance("^b-d");

    // "d-b" is a reversed range; CharSet normalises it to the same as "b-d".
    private final CharSet reversedRange_D_to_B = CharSet.getInstance("d-b");

    @Test
    void testContains_withRangeExpression_matchesCharsInsideRangeOnly() {
        assertFalse(rangeB_to_D.contains('a'), "'a' is before the range start 'b'");
        assertTrue(rangeB_to_D.contains('b'),  "'b' is the range start");
        assertTrue(rangeB_to_D.contains('c'),  "'c' is inside the range");
        assertTrue(rangeB_to_D.contains('d'),  "'d' is the range end");
        assertFalse(rangeB_to_D.contains('e'), "'e' is after the range end 'd'");
    }

    @Test
    void testContains_withIndividualCharList_matchesListedCharsOnly() {
        assertFalse(individualBCD.contains('a'), "'a' is not listed");
        assertTrue(individualBCD.contains('b'),  "'b' is listed");
        assertTrue(individualBCD.contains('c'),  "'c' is listed");
        assertTrue(individualBCD.contains('d'),  "'d' is listed");
        assertFalse(individualBCD.contains('e'), "'e' is not listed");
    }

    @Test
    void testContains_withNonConsecutiveCharList_doesNotMatchGapChars() {
        assertFalse(individualBD.contains('a'), "'a' is not listed");
        assertTrue(individualBD.contains('b'),  "'b' is listed");
        assertFalse(individualBD.contains('c'), "'c' is not listed (gap between 'b' and 'd')");
        assertTrue(individualBD.contains('d'),  "'d' is listed");
        assertFalse(individualBD.contains('e'), "'e' is not listed");
    }

    @Test
    void testContains_withNegatedRange_matchesCharsOutsideRangeOnly() {
        assertTrue(negatedB_to_D.contains('a'),  "'a' is outside the negated range");
        assertFalse(negatedB_to_D.contains('b'), "'b' is inside the negated range, so excluded");
        assertFalse(negatedB_to_D.contains('c'), "'c' is inside the negated range, so excluded");
        assertFalse(negatedB_to_D.contains('d'), "'d' is inside the negated range, so excluded");
        assertTrue(negatedB_to_D.contains('e'),  "'e' is outside the negated range");
    }

    @Test
    void testContains_withReversedRange_normalisesToSameAsForwardRange() {
        // A reversed range "d-b" is treated identically to "b-d".
        assertFalse(reversedRange_D_to_B.contains('a'), "'a' is before the normalised range");
        assertTrue(reversedRange_D_to_B.contains('b'),  "'b' is in the normalised range");
        assertTrue(reversedRange_D_to_B.contains('c'),  "'c' is in the normalised range");
        assertTrue(reversedRange_D_to_B.contains('d'),  "'d' is in the normalised range");
        assertFalse(reversedRange_D_to_B.contains('e'), "'e' is after the normalised range");

        Set<CharRange> ranges = reversedRange_D_to_B.getCharRanges();
        assertEquals(1, ranges.size(),          "reversed range should collapse to exactly one CharRange");
        assertEquals("[b-d]", reversedRange_D_to_B.toString(), "normalised string should be [b-d]");
    }
}
