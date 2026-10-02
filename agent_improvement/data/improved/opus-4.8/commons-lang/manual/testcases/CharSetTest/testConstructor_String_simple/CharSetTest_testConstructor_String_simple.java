package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharSet#getInstance(String...)} parses each of the basic
 * set-definition syntaxes into the expected {@link CharRange} contents.
 */
public class CharSetTest_testConstructor_String_simple extends AbstractLangTest {

    @Test
    void testConstructor_String_simple() {
        // A null definition produces an empty set (no ranges).
        assertEmptyCharSet(CharSet.getInstance((String) null));

        // An empty definition string also produces an empty set.
        assertEmptyCharSet(CharSet.getInstance(""));

        // "a" -> a single "is" range for the character 'a'.
        assertSingleRangeCharSet(CharSet.getInstance("a"), "[a]", "a");

        // "^a" -> a single negated-character range.
        assertSingleRangeCharSet(CharSet.getInstance("^a"), "[^a]", "^a");

        // "a-e" -> a single character range from 'a' to 'e'.
        assertSingleRangeCharSet(CharSet.getInstance("a-e"), "[a-e]", "a-e");

        // "^a-e" -> a single negated character range from 'a' to 'e'.
        assertSingleRangeCharSet(CharSet.getInstance("^a-e"), "[^a-e]", "^a-e");
    }

    /**
     * Asserts that the set contains no ranges and renders as "[]".
     */
    private static void assertEmptyCharSet(final CharSet set) {
        assertEquals("[]", set.toString());
        assertEquals(0, set.getCharRanges().size());
    }

    /**
     * Asserts that the set contains exactly one range with the expected
     * {@code toString()} renderings for both the set and its single range.
     *
     * @param set                the CharSet under test
     * @param expectedSetString  expected {@link CharSet#toString()}
     * @param expectedRangeString expected {@link CharRange#toString()} of the only range
     */
    private static void assertSingleRangeCharSet(final CharSet set,
            final String expectedSetString, final String expectedRangeString) {
        final Set<CharRange> ranges = set.getCharRanges();
        assertEquals(expectedSetString, set.toString());
        assertEquals(1, ranges.size());
        assertEquals(expectedRangeString, ranges.iterator().next().toString());
    }
}
