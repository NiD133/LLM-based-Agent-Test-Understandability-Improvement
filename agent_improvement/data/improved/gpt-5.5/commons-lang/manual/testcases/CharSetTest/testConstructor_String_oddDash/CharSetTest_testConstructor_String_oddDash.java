package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_oddDash extends AbstractLangTest {

    @Test
    void testConstructor_String_oddDash() {
        assertCharRanges("-", 1, CharRange.is('-'));
        assertCharRanges("--", 1, CharRange.is('-'));
        assertCharRanges("---", 1, CharRange.is('-'));
        assertCharRanges("----", 1, CharRange.is('-'));

        assertCharRanges("-a", 2, CharRange.is('-'), CharRange.is('a'));
        assertCharRanges("a-", 2, CharRange.is('a'), CharRange.is('-'));

        assertCharRanges("a--", 1, CharRange.isIn('a', '-'));
        assertCharRanges("--a", 1, CharRange.isIn('-', 'a'));
    }

    private void assertCharRanges(final String pattern, final int expectedSize, final CharRange... expectedRanges) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertEquals(expectedSize, ranges.size());
        for (final CharRange expectedRange : expectedRanges) {
            assertTrue(ranges.contains(expectedRange));
        }
    }
}
