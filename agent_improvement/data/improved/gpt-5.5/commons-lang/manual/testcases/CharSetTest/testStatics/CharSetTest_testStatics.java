package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testStatics extends AbstractLangTest {

    @Test
    void testStatics() {
        assertCharSetRanges(CharSet.EMPTY, 0);
        assertCharSetRanges(CharSet.ASCII_ALPHA, 2, CharRange.isIn('a', 'z'), CharRange.isIn('A', 'Z'));
        assertCharSetRanges(CharSet.ASCII_ALPHA_LOWER, 1, CharRange.isIn('a', 'z'));
        assertCharSetRanges(CharSet.ASCII_ALPHA_UPPER, 1, CharRange.isIn('A', 'Z'));
        assertCharSetRanges(CharSet.ASCII_NUMERIC, 1, CharRange.isIn('0', '9'));
    }

    private void assertCharSetRanges(final CharSet charSet, final int expectedSize, final CharRange... expectedRanges) {
        final Set<CharRange> ranges = charSet.getCharRanges();

        assertEquals(expectedSize, ranges.size());
        for (final CharRange expectedRange : expectedRanges) {
            assertTrue(ranges.contains(expectedRange));
        }
    }
}
