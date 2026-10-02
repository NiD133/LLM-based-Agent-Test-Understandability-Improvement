package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_combo extends AbstractLangTest {

    // Each test method covers one combination pattern supported by CharSet.getInstance(String).
    // The pattern grammar: single char ("a"), range ("a-c"), and negated forms (^).
    // These tests exercise only the non-negated combination cases.

    @Test
    void testConstructor_multipleSingleChars() {
        // "abc" → three individual CharRange entries, one per character
        CharSet set = CharSet.getInstance("abc");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(3, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('b')));
        assertTrue(ranges.contains(CharRange.is('c')));
    }

    @Test
    void testConstructor_multipleRanges() {
        // "a-ce-f" → two consecutive range tokens: [a..c] and [e..f]
        CharSet set = CharSet.getInstance("a-ce-f");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'c')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
    }

    @Test
    void testConstructor_singleCharThenRange() {
        // "ae-f" → single char 'a' followed immediately by range [e..f]
        CharSet set = CharSet.getInstance("ae-f");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
    }

    @Test
    void testConstructor_rangeThenSingleChar() {
        // "e-fa" → range [e..f] followed by single char 'a' (order in string is reversed vs previous test)
        CharSet set = CharSet.getInstance("e-fa");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
    }

    @Test
    void testConstructor_complexCombinationOfSingleCharsAndRanges() {
        // "ae-fm-pz" → four tokens: single 'a', range [e..f], range [m..p], single 'z'
        CharSet set = CharSet.getInstance("ae-fm-pz");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(4, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
        assertTrue(ranges.contains(CharRange.isIn('m', 'p')));
        assertTrue(ranges.contains(CharRange.is('z')));
    }
}
