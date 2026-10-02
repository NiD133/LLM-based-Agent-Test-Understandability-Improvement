package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link CharSet#getInstance(String...)} parses the negation
 * character {@code ^}, focusing on the tricky cases where an odd or even
 * number of carets changes whether a caret negates the following character,
 * is treated as a literal, or forms a negated range.
 */
public class CharSetTest_testConstructor_String_oddNegate extends AbstractLangTest {

    /**
     * Asserts that parsing {@code definition} produces exactly the given
     * character ranges (no more, no fewer).
     *
     * @param definition     the set-definition string to parse
     * @param expectedRanges the ranges the resulting CharSet must contain
     */
    private void assertCharRanges(final String definition, final CharRange... expectedRanges) {
        final Set<CharRange> actualRanges = CharSet.getInstance(definition).getCharRanges();
        assertEquals(expectedRanges.length, actualRanges.size(),
                () -> "wrong number of ranges parsed from \"" + definition + "\"");
        for (final CharRange expected : expectedRanges) {
            assertTrue(actualRanges.contains(expected),
                    () -> "\"" + definition + "\" should contain range " + expected);
        }
    }

    @Test
    void testConstructor_String_oddNegate() {
        // A single trailing caret has nothing to negate, so it is a literal '^'.
        assertCharRanges("^", CharRange.is('^'));

        // Two carets negate one another: '^' negates the following literal '^'.
        assertCharRanges("^^", CharRange.isNot('^'));

        // Three carets: a negated '^' followed by a leftover literal '^'.
        assertCharRanges("^^^", CharRange.isNot('^'), CharRange.is('^'));

        // Four carets collapse to a single negated '^' (the duplicate is dropped).
        assertCharRanges("^^^^", CharRange.isNot('^'));

        // A literal 'a' followed by a trailing literal '^'.
        assertCharRanges("a^", CharRange.is('a'), CharRange.is('^'));

        // "^a" negates 'a'; the trailing '-' is a literal dash.
        assertCharRanges("^a-", CharRange.isNot('a'), CharRange.is('-'));

        // "^^-c" is a negated range from '^' to 'c'.
        assertCharRanges("^^-c", CharRange.isNotIn('^', 'c'));

        // "^c-^" is a negated range from 'c' to '^'.
        assertCharRanges("^c-^", CharRange.isNotIn('c', '^'));

        // Negated range "^c-^" followed by a leftover literal 'd'.
        assertCharRanges("^c-^d", CharRange.isNotIn('c', '^'), CharRange.is('d'));

        // "^^" negates '^'; the trailing '-' is a literal dash.
        assertCharRanges("^^-", CharRange.isNot('^'), CharRange.is('-'));
    }
}
