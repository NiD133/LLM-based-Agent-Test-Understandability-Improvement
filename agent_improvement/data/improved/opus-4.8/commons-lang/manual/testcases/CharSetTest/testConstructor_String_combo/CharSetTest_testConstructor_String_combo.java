package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharSet#getInstance(String...)} parses combination patterns
 * (mixtures of single characters and "x-y" ranges) into the expected set of
 * {@link CharRange} objects.
 */
public class CharSetTest_testConstructor_String_combo extends AbstractLangTest {

    /**
     * Parses the given pattern and asserts that the resulting {@link CharSet}
     * contains exactly the supplied char ranges (no more, no less).
     *
     * @param pattern        the CharSet definition string to parse
     * @param expectedRanges the char ranges the parsed set must contain
     */
    private static void assertCharRanges(final String pattern, final CharRange... expectedRanges) {
        final Set<CharRange> ranges = CharSet.getInstance(pattern).getCharRanges();
        assertEquals(expectedRanges.length, ranges.size(),
                "Unexpected number of ranges for pattern \"" + pattern + "\"");
        for (final CharRange expected : expectedRanges) {
            assertTrue(ranges.contains(expected),
                    "Pattern \"" + pattern + "\" should contain range " + expected);
        }
    }

    @Test
    void testConstructor_String_combo() {
        // Three separate single characters.
        assertCharRanges("abc",
                CharRange.is('a'), CharRange.is('b'), CharRange.is('c'));

        // Two adjacent ranges.
        assertCharRanges("a-ce-f",
                CharRange.isIn('a', 'c'), CharRange.isIn('e', 'f'));

        // A single character followed by a range.
        assertCharRanges("ae-f",
                CharRange.is('a'), CharRange.isIn('e', 'f'));

        // A range followed by a single character (order is irrelevant to the result).
        assertCharRanges("e-fa",
                CharRange.is('a'), CharRange.isIn('e', 'f'));

        // A mix of single characters and ranges.
        assertCharRanges("ae-fm-pz",
                CharRange.is('a'), CharRange.isIn('e', 'f'),
                CharRange.isIn('m', 'p'), CharRange.is('z'));
    }
}
