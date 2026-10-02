package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests how CharSet.getInstance() handles dash ('-') characters in unusual positions.
 *
 * The CharSet parser treats '-' as a range operator only when it appears between
 * two non-dash characters (e.g. "a-z"). A dash that cannot form a valid range
 * (at the start, end, or between two dashes) is treated as a literal character.
 */
public class CharSetTest_testConstructor_String_oddDash extends AbstractLangTest {

    // -----------------------------------------------------------------------
    // Strings consisting entirely of dashes: always collapse to a single literal '-'
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Single '-' is treated as literal dash character")
    void testSingleDash_isLiteralDash() {
        CharSet set = CharSet.getInstance("-");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
    }

    @Test
    @DisplayName("Double '--' collapses to a single literal dash (range '-' to '-')")
    void testDoubleDash_collapseToSingleLiteralDash() {
        CharSet set = CharSet.getInstance("--");
        Set<CharRange> ranges = set.getCharRanges();

        // "--" is parsed as the range '-' to '-', which reduces to just '-'
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
    }

    @Test
    @DisplayName("Triple '---' collapses to a single literal dash")
    void testTripleDash_collapseToSingleLiteralDash() {
        CharSet set = CharSet.getInstance("---");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
    }

    @Test
    @DisplayName("Quadruple '----' collapses to a single literal dash")
    void testQuadrupleDash_collapseToSingleLiteralDash() {
        CharSet set = CharSet.getInstance("----");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
    }

    // -----------------------------------------------------------------------
    // Dash at the boundary of a string with one other character:
    // cannot form a range, so both characters are independent literals
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Leading '-a': dash at start cannot start a range, yielding literals '-' and 'a'")
    void testLeadingDash_dashAndCharAreSeparateLiterals() {
        CharSet set = CharSet.getInstance("-a");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
        assertTrue(ranges.contains(CharRange.is('a')));
    }

    @Test
    @DisplayName("Trailing 'a-': dash at end cannot complete a range, yielding literals 'a' and '-'")
    void testTrailingDash_charAndDashAreSeparateLiterals() {
        CharSet set = CharSet.getInstance("a-");
        Set<CharRange> ranges = set.getCharRanges();

        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('-')));
    }

    // -----------------------------------------------------------------------
    // Dash between a letter and another dash, or between two dashes and a letter:
    // the middle '-' acts as a range operator
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("'a--': middle '-' is the range operator, producing the range 'a' to '-'")
    void testCharDashDash_middleDashIsRangeOperator() {
        CharSet set = CharSet.getInstance("a--");
        Set<CharRange> ranges = set.getCharRanges();

        // Parsed as the range from 'a' to '-' (reversed automatically if needed)
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', '-')));
    }

    @Test
    @DisplayName("'--a': middle '-' is the range operator, producing the range '-' to 'a'")
    void testDashDashChar_middleDashIsRangeOperator() {
        CharSet set = CharSet.getInstance("--a");
        Set<CharRange> ranges = set.getCharRanges();

        // Parsed as the range from '-' to 'a'
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('-', 'a')));
    }
}
