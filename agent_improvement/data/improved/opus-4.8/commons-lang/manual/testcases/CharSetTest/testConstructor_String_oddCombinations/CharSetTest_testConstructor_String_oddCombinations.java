package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link CharSet#getInstance(String...)} parses "odd" definition strings,
 * i.e. strings where the literal caret ({@code ^}) and dash ({@code -}) characters
 * collide with the negation / range syntax.
 *
 * <p>The parser scans left to right and, at each position, greedily applies the first
 * matching rule (negated range "^x-y", then range "x-y", then negated char "^x",
 * then literal char "x"). Each scenario below documents exactly how a string is
 * split into {@link CharRange ranges} and which characters the resulting set matches.</p>
 */
public class CharSetTest_testConstructor_String_oddCombinations extends AbstractLangTest {

    @Test
    void testConstructor_String_oddCombinations() {
        // "a-^c" splits as the range "a-^" followed by the literal "c".
        // The range endpoints '^' (94) and 'a' (97) are reversed internally,
        // so it matches every character between '^' and 'a' inclusive.
        Set<CharRange> ranges = CharSet.getInstance("a-^c").getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('a', '^')), "range 'a-^'");
        assertTrue(ranges.contains(CharRange.is('c')), "literal 'c'");
        CharSet aDashCaretC = CharSet.getInstance("a-^c");
        assertFalse(aDashCaretC.contains('b'), "'b' is above 'a', outside '^'..'a'");
        assertTrue(aDashCaretC.contains('^'), "'^' is the lower endpoint");
        assertTrue(aDashCaretC.contains('_'), "'_' lies between '^' and 'a'");
        assertTrue(aDashCaretC.contains('c'), "'c' is the trailing literal");

        // "^a-^c" splits as the negated range "^a-^" followed by the literal "c".
        // It matches every character EXCEPT those between '^' and 'a' inclusive.
        ranges = CharSet.getInstance("^a-^c").getCharRanges();
        assertTrue(ranges.contains(CharRange.isNotIn('a', '^')), "negated range '^a-^'");
        assertTrue(ranges.contains(CharRange.is('c')), "literal 'c'");
        CharSet caretADashCaretC = CharSet.getInstance("^a-^c");
        assertTrue(caretADashCaretC.contains('b'), "'b' is outside the negated '^'..'a'");
        assertFalse(caretADashCaretC.contains('^'), "'^' is inside the negated range");
        assertFalse(caretADashCaretC.contains('_'), "'_' is inside the negated range");

        // "a- ^-- " splits as the range "a- " (space end) followed by the
        // negated range "^-- " (literal '-' to space). Together they cover everything.
        ranges = CharSet.getInstance("a- ^-- ").getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('a', ' ')), "range 'a- '");
        assertTrue(ranges.contains(CharRange.isNotIn('-', ' ')), "negated range '^-- '");
        CharSet coversEverything = CharSet.getInstance("a- ^-- ");
        assertTrue(coversEverything.contains('#'));
        assertTrue(coversEverything.contains('^'));
        assertTrue(coversEverything.contains('a'));
        assertTrue(coversEverything.contains('*'));
        assertTrue(coversEverything.contains('A'));

        // "^-b" is a single range from '^' to 'b' (the leading '^' is a range
        // endpoint here, not a negation, because "^x-" needs a 4th char to negate).
        ranges = CharSet.getInstance("^-b").getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('^', 'b')), "range '^-b'");
        CharSet caretToB = CharSet.getInstance("^-b");
        assertTrue(caretToB.contains('b'), "'b' is the upper endpoint");
        assertTrue(caretToB.contains('_'), "'_' lies between '^' and 'b'");
        assertFalse(caretToB.contains('A'), "'A' (65) is below '^' (94)");
        assertTrue(caretToB.contains('^'), "'^' is the lower endpoint");

        // "b-^" is the same range as "^-b" with reversed (auto-corrected) endpoints.
        ranges = CharSet.getInstance("b-^").getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('^', 'b')), "range 'b-^' == '^-b'");
        CharSet bToCaret = CharSet.getInstance("b-^");
        assertTrue(bToCaret.contains('b'), "'b' is an endpoint");
        assertTrue(bToCaret.contains('^'), "'^' is an endpoint");
        assertTrue(bToCaret.contains('a'), "'a' lies between '^' and 'b'");
        assertFalse(bToCaret.contains('c'), "'c' (99) is above 'b' (98)");
    }
}
