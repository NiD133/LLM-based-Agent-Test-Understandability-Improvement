package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link CharSet#getInstance(String...)} parses dash ('-') characters.
 *
 * <p>The dash is special because it also denotes a range (e.g. "a-c"). These cases
 * confirm how the parser treats dashes that appear on their own, repeated, or next
 * to another character.</p>
 */
public class CharSetTest_testConstructor_String_oddDash extends AbstractLangTest {

    /** Parses the given definition and returns its set of character ranges. */
    private static Set<CharRange> charRangesOf(final String definition) {
        return CharSet.getInstance(definition).getCharRanges();
    }

    @Test
    void testConstructor_String_oddDash() {
        // A lone dash, or any run of only dashes, collapses to the single literal '-'.
        for (final String onlyDashes : new String[] {"-", "--", "---", "----"}) {
            final Set<CharRange> ranges = charRangesOf(onlyDashes);
            assertEquals(1, ranges.size(), onlyDashes);
            assertTrue(ranges.contains(CharRange.is('-')), onlyDashes);
        }

        // A dash next to a single other character stays two separate literals,
        // because a range needs the dash to sit between two characters.
        Set<CharRange> ranges = charRangesOf("-a");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
        assertTrue(ranges.contains(CharRange.is('a')));

        ranges = charRangesOf("a-");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('-')));

        // With three characters, the parser reads a range from the first to the third
        // character; the middle dash is the range separator.
        ranges = charRangesOf("a--");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', '-')));

        ranges = charRangesOf("--a");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('-', 'a')));
    }
}
