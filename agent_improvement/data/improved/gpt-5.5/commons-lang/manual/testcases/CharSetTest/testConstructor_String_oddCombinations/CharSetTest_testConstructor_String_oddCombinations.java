package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_oddCombinations extends AbstractLangTest {

    @Test
    void testConstructor_String_oddCombinations() {
        assertRangeEndingBeforeLiteral("a-^c");
        assertNegatedRangeEndingBeforeLiteral("^a-^c");
        assertOverlappingOrdinaryAndNegatedRanges("a- ^-- ");
        assertRangeStartingWithCaret("^-b");
        assertReversedRangeEndingWithCaret("b-^");
    }

    private void assertRangeEndingBeforeLiteral(final String pattern) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('a', '^')));
        assertTrue(ranges.contains(CharRange.is('c')));

        assertFalse(set.contains('b'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('_'));
        assertTrue(set.contains('c'));
    }

    private void assertNegatedRangeEndingBeforeLiteral(final String pattern) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isNotIn('a', '^')));
        assertTrue(ranges.contains(CharRange.is('c')));

        assertTrue(set.contains('b'));
        assertFalse(set.contains('^'));
        assertFalse(set.contains('_'));
    }

    private void assertOverlappingOrdinaryAndNegatedRanges(final String pattern) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('a', ' ')));
        assertTrue(ranges.contains(CharRange.isNotIn('-', ' ')));

        assertTrue(set.contains('#'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('a'));
        assertTrue(set.contains('*'));
        assertTrue(set.contains('A'));
    }

    private void assertRangeStartingWithCaret(final String pattern) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('^', 'b')));

        assertTrue(set.contains('b'));
        assertTrue(set.contains('_'));
        assertFalse(set.contains('A'));
        assertTrue(set.contains('^'));
    }

    private void assertReversedRangeEndingWithCaret(final String pattern) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('^', 'b')));

        assertTrue(set.contains('b'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('a'));
        assertFalse(set.contains('c'));
    }
}
