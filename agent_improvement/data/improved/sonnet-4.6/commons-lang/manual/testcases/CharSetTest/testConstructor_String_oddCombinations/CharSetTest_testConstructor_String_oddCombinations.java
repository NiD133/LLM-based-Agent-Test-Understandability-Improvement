package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests for CharSet.getInstance() with "odd" range combinations where '^' appears
 * mid-string as both a negation prefix and a literal range endpoint.
 *
 * Parsing rules (applied left-to-right, longest match first):
 *   "^X-Y" (4 chars, starts with ^, 3rd char is '-') -> negated range  isNotIn(X, Y)
 *   "X-Y"  (3 chars, 2nd char is '-')                -> ordinary range isIn(X, Y)
 *   "^X"   (2 chars, starts with ^)                  -> negated single isNot(X)
 *   "X"    (1 char)                                   -> single char    is(X)
 *
 * Note: CharRange.isIn() / isNotIn() normalises start/end order, so
 * isIn('a','^') == isIn('^','a')  ('^' is ASCII 94, 'a' is 97).
 */
public class CharSetTest_testConstructor_String_oddCombinations extends AbstractLangTest {

    /**
     * "a-^c" is parsed as two tokens:
     *   "a-^"  -> ordinary range isIn('a', '^')   (3-char rule, 2nd char is '-')
     *   "c"    -> single char    is('c')
     *
     * ASCII order: '^'(94) < '_'(95) < '`'(96) < 'a'(97), so the range covers
     * '^', '_', '`', 'a'.  'b' is NOT in the range.
     */
    @Test
    void testRangeEndingAtCaret_followedBySingleChar() {
        CharSet set = CharSet.getInstance("a-^c");
        Set<CharRange> ranges = set.getCharRanges();

        // verify that the two expected CharRange objects were created
        assertTrue(ranges.contains(CharRange.isIn('a', '^')),
                "expected ordinary range isIn('a','^')");
        assertTrue(ranges.contains(CharRange.is('c')),
                "expected single-char range is('c')");

        // 'b' (98) lies above 'a' (97) and outside the range '^'(94)-'a'(97)
        assertFalse(set.contains('b'), "'b' should not be in range [^..a] or {c}");

        // '^'(94) is an endpoint of the range
        assertTrue(set.contains('^'), "'^' is an endpoint of the range");

        // '_'(95) sits between '^'(94) and 'a'(97)
        assertTrue(set.contains('_'), "'_' lies inside the range [^..a]");

        // 'c' is the explicit single-char token
        assertTrue(set.contains('c'), "'c' was added as a standalone character");
    }

    /**
     * "^a-^c" is parsed as two tokens:
     *   "^a-^" -> negated range isNotIn('a', '^')  (4-char rule: starts with '^', 3rd char '-')
     *   "c"    -> single char   is('c')
     *
     * isNotIn('a','^') covers everything except '^'(94)...'a'(97).
     * So '^' and '_' are excluded; 'b' (98) is above 'a' (97) and therefore included.
     */
    @Test
    void testNegatedRangeEndingAtCaret_followedBySingleChar() {
        CharSet set = CharSet.getInstance("^a-^c");
        Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isNotIn('a', '^')),
                "expected negated range isNotIn('a','^')");
        assertTrue(ranges.contains(CharRange.is('c')),
                "expected single-char range is('c')");

        // 'b'(98) > 'a'(97), so it is outside the excluded range and is contained
        assertTrue(set.contains('b'), "'b' is outside the excluded range [^..a]");

        // '^'(94) is inside the excluded range [^..a], so it is absent
        assertFalse(set.contains('^'), "'^' is within the negated range and should be excluded");

        // '_'(95) is inside the excluded range [^..a], so it is absent
        assertFalse(set.contains('_'), "'_' is within the negated range and should be excluded");
    }

    /**
     * "a- ^-- " is parsed as two tokens:
     *   "a- "  -> ordinary range isIn('a', ' ')    (3-char rule; space is a valid boundary)
     *   "^-- " -> negated range  isNotIn('-', ' ') (4-char rule: starts with '^', 3rd char '-')
     *
     * isIn('a',' ') covers ' '(32)...'a'(97) (order normalised).
     * isNotIn('-',' ') excludes ' '(32)...'-'(45).
     *
     * Together these two ranges cover virtually the entire character space,
     * because any character not in ' '...'a' is outside the negated range.
     */
    @Test
    void testRangeToSpace_andNegatedRangeOfHyphensAndSpace() {
        CharSet set = CharSet.getInstance("a- ^-- ");
        Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('a', ' ')),
                "expected ordinary range isIn('a',' ')");
        assertTrue(ranges.contains(CharRange.isNotIn('-', ' ')),
                "expected negated range isNotIn('-',' ')");

        // '#'(35) is in ' '(32)...'a'(97), covered by the ordinary range
        assertTrue(set.contains('#'), "'#' is in the range [' '..'a']");

        // '^'(94) is in ' '(32)...'a'(97), covered by the ordinary range
        assertTrue(set.contains('^'), "'^' is in the range [' '..'a']");

        // 'a'(97) is the upper boundary of the ordinary range
        assertTrue(set.contains('a'), "'a' is the upper boundary of the ordinary range");

        // '*'(42) is in ' '(32)...'a'(97), covered by the ordinary range
        assertTrue(set.contains('*'), "'*' is in the range [' '..'a']");

        // 'A'(65) is in ' '(32)...'a'(97), covered by the ordinary range
        assertTrue(set.contains('A'), "'A' is in the range [' '..'a']");
    }

    /**
     * "^-b" is parsed as a single token:
     *   "^-b" -> ordinary range isIn('^', 'b')  (3-char rule, 2nd char '-')
     *
     * '^'(94)...'b'(98) covers: '^','_','`','a','b'.
     * 'A'(65) is well below '^'(94) and is not covered.
     */
    @Test
    void testRangeFromCaretToLowercaseB() {
        CharSet set = CharSet.getInstance("^-b");
        Set<CharRange> ranges = set.getCharRanges();

        assertTrue(ranges.contains(CharRange.isIn('^', 'b')),
                "expected ordinary range isIn('^','b')");

        // 'b'(98) is the upper boundary
        assertTrue(set.contains('b'), "'b' is the upper boundary of the range");

        // '_'(95) sits between '^'(94) and 'b'(98)
        assertTrue(set.contains('_'), "'_' lies inside the range [^..b]");

        // 'A'(65) is below '^'(94)
        assertFalse(set.contains('A'), "'A' is below the range start '^'(94)");

        // '^'(94) is the lower boundary
        assertTrue(set.contains('^'), "'^' is the lower boundary of the range");
    }

    /**
     * "b-^" is parsed as a single token:
     *   "b-^" -> ordinary range isIn('b', '^')  (3-char rule, 2nd char '-')
     *
     * CharRange normalises start/end, so isIn('b','^') == isIn('^','b').
     * '^'(94)...'b'(98) covers: '^','_','`','a','b'.
     * 'c'(99) is just above 'b'(98) and is not covered.
     */
    @Test
    void testRangeFromLowercaseBToCaretNormalisesOrder() {
        CharSet set = CharSet.getInstance("b-^");
        Set<CharRange> ranges = set.getCharRanges();

        // start/end are normalised to ('^','b') regardless of input order
        assertTrue(ranges.contains(CharRange.isIn('^', 'b')),
                "isIn('b','^') is stored as isIn('^','b') after normalisation");

        // 'b'(98) is the upper boundary
        assertTrue(set.contains('b'), "'b' is the upper boundary of the normalised range");

        // '^'(94) is the lower boundary
        assertTrue(set.contains('^'), "'^' is the lower boundary of the normalised range");

        // 'a'(97) sits between '^'(94) and 'b'(98)
        assertTrue(set.contains('a'), "'a' lies inside the range [^..b]");

        // 'c'(99) is just above 'b'(98)
        assertFalse(set.contains('c'), "'c' is above the upper boundary 'b'(98)");
    }
}
