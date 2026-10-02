package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testStatics extends AbstractLangTest {

    @Test
    void testEmptyCharSet_hasNoCharRanges() {
        Set<CharRange> ranges = CharSet.EMPTY.getCharRanges();
        assertEquals(0, ranges.size());
    }

    @Test
    void testAsciiAlphaCharSet_containsBothUpperAndLowerCaseRanges() {
        Set<CharRange> ranges = CharSet.ASCII_ALPHA.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'z')));
        assertTrue(ranges.contains(CharRange.isIn('A', 'Z')));
    }

    @Test
    void testAsciiAlphaLowerCharSet_containsOnlyLowerCaseRange() {
        Set<CharRange> ranges = CharSet.ASCII_ALPHA_LOWER.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'z')));
    }

    @Test
    void testAsciiAlphaUpperCharSet_containsOnlyUpperCaseRange() {
        Set<CharRange> ranges = CharSet.ASCII_ALPHA_UPPER.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('A', 'Z')));
    }

    @Test
    void testAsciiNumericCharSet_containsOnlyDigitRange() {
        Set<CharRange> ranges = CharSet.ASCII_NUMERIC.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('0', '9')));
    }
}
