package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_comboNegated extends AbstractLangTest {

    @Test
    void testConstructor_String_comboNegated() {
        assertCharRanges("^abc", 3, CharRange.isNot('a'), CharRange.is('b'), CharRange.is('c'));
        assertCharRanges("b^ac", 3, CharRange.is('b'), CharRange.isNot('a'), CharRange.is('c'));
        assertCharRanges("db^ac", 4, CharRange.is('d'), CharRange.is('b'), CharRange.isNot('a'), CharRange.is('c'));
        assertCharRanges("^b^a", 2, CharRange.isNot('b'), CharRange.isNot('a'));
        assertCharRanges("b^a-c^z", 3, CharRange.isNotIn('a', 'c'), CharRange.isNot('z'), CharRange.is('b'));
    }

    private void assertCharRanges(final String pattern, final int expectedRangeCount, final CharRange... expectedRanges) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertEquals(expectedRangeCount, ranges.size());
        for (final CharRange expectedRange : expectedRanges) {
            assertTrue(ranges.contains(expectedRange));
        }
    }
}
