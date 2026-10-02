package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link CharSet#getInstance(String...)} parses set-definition strings that
 * mix negated elements ("^x") with ordinary characters and ranges.
 *
 * <p>For each definition string the test checks both the number of parsed
 * {@link CharRange ranges} and that every expected range is present.</p>
 */
public class CharSetTest_testConstructor_String_comboNegated extends AbstractLangTest {

    /**
     * Asserts that parsing {@code definition} yields exactly the {@code expectedRanges}.
     *
     * @param definition     the set-definition string passed to {@link CharSet#getInstance(String...)}
     * @param expectedRanges the ranges the resulting set must contain (also defines the expected size)
     */
    private static void assertParsesTo(final String definition, final CharRange... expectedRanges) {
        final Set<CharRange> ranges = CharSet.getInstance(definition).getCharRanges();
        assertEquals(expectedRanges.length, ranges.size(),
                () -> "Unexpected number of ranges for \"" + definition + "\"");
        for (final CharRange expected : expectedRanges) {
            assertTrue(ranges.contains(expected),
                    () -> "\"" + definition + "\" should contain " + expected);
        }
    }

    @Test
    void testConstructor_String_comboNegated() {
        // Negation prefix applies only to the immediately following character.
        assertParsesTo("^abc",
                CharRange.isNot('a'), CharRange.is('b'), CharRange.is('c'));

        // A '^' in the middle negates just the next character; order does not matter to set membership.
        assertParsesTo("b^ac",
                CharRange.is('b'), CharRange.isNot('a'), CharRange.is('c'));

        // Leading plain character followed by a mid-string negation.
        assertParsesTo("db^ac",
                CharRange.is('d'), CharRange.is('b'), CharRange.isNot('a'), CharRange.is('c'));

        // Two independent negated single characters.
        assertParsesTo("^b^a",
                CharRange.isNot('b'), CharRange.isNot('a'));

        // Mix of a plain char, a negated range ("^a-c") and a negated single char ("^z").
        assertParsesTo("b^a-c^z",
                CharRange.isNotIn('a', 'c'), CharRange.isNot('z'), CharRange.is('b'));
    }
}
