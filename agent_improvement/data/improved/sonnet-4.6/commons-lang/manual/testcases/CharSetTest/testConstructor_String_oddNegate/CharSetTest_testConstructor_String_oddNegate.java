package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests CharSet construction with patterns containing caret ('^') characters in unusual positions.
 *
 * <p>The CharSet DSL parses patterns left-to-right using these rules (highest priority first):</p>
 * <ol>
 *   <li>Four chars matching {@code ^X-Y} → negated range "not X..Y"</li>
 *   <li>Three chars matching {@code X-Y}  → ordinary range "X..Y"</li>
 *   <li>Two chars matching {@code ^X}     → negated single char "not X"</li>
 *   <li>Single char {@code X}            → literal char "X"</li>
 * </ol>
 *
 * <p>A lone trailing {@code ^} is consumed as rule 4, becoming a literal {@code ^}.
 * Duplicate ranges are automatically collapsed to a single entry.</p>
 */
public class CharSetTest_testConstructor_String_oddNegate extends AbstractLangTest {

    @Test
    void testConstructor_String_oddNegate() {
        CharSet set;
        Set<CharRange> ranges;

        // "^" — a lone caret has no following char to negate, so rule 4 fires:
        // it is stored as the literal character '^'.
        set = CharSet.getInstance("^");
        ranges = set.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('^')));

        // "^^" — rule 3 fires on the two carets: the second '^' is the negated char,
        // yielding one range: "not '^'".
        set = CharSet.getInstance("^^");
        ranges = set.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^')));

        // "^^^" — rule 3 consumes the first two carets as "not '^'", then rule 4
        // consumes the third as the literal '^', yielding two distinct ranges.
        set = CharSet.getInstance("^^^");
        ranges = set.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^')));
        assertTrue(ranges.contains(CharRange.is('^')));

        // "^^^^" — rule 3 fires twice producing "not '^'" both times; duplicates
        // are collapsed, leaving exactly one range.
        set = CharSet.getInstance("^^^^");
        ranges = set.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^')));

        // "a^" — rule 4 fires for 'a' (literal 'a'), then rule 4 fires for the
        // trailing '^' (literal '^'), yielding two ranges.
        set = CharSet.getInstance("a^");
        ranges = set.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('^')));

        // "^a-" — rule 3 fires on "^a" (not 'a'); the remaining '-' has no right-hand
        // char for a range, so rule 4 fires and stores it as the literal '-'.
        set = CharSet.getInstance("^a-");
        ranges = set.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('a')));
        assertTrue(ranges.contains(CharRange.is('-')));

        // "^^-c" — rule 1 fires on all four chars: '^' is the negation prefix,
        // so the result is a negated range from '^' to 'c'.
        set = CharSet.getInstance("^^-c");
        ranges = set.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('^', 'c')));

        // "^c-^" — rule 1 fires: negated range from 'c' to '^'.
        set = CharSet.getInstance("^c-^");
        ranges = set.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('c', '^')));

        // "^c-^d" — rule 1 consumes "^c-^" as a negated range from 'c' to '^';
        // rule 4 then consumes 'd' as a literal, yielding two ranges.
        set = CharSet.getInstance("^c-^d");
        ranges = set.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('c', '^')));
        assertTrue(ranges.contains(CharRange.is('d')));

        // "^^-" — rule 3 fires on "^^" (not '^'); the remaining '-' is a trailing
        // char with no range context, so rule 4 stores it as the literal '-'.
        set = CharSet.getInstance("^^-");
        ranges = set.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^')));
        assertTrue(ranges.contains(CharRange.is('-')));
    }
}
