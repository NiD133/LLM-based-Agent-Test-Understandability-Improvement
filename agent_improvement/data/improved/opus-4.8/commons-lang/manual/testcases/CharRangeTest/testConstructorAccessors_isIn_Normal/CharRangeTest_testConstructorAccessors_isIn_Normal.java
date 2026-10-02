package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a non-negated {@link CharRange} created via {@link CharRange#isIn(char, char)}
 * exposes the start and end characters it was built from and reports itself as not negated.
 */
public class CharRangeTest_testConstructorAccessors_isIn_Normal extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Normal() {
        // Build an inclusive range covering the characters 'a' through 'e'.
        final CharRange range = CharRange.isIn('a', 'e');

        // The accessors should report the exact bounds passed to the factory method.
        assertEquals('a', range.getStart(), "start should be the first character of the range");
        assertEquals('e', range.getEnd(), "end should be the last character of the range");

        // isIn() builds a normal (non-negated) range.
        assertFalse(range.isNegated(), "a range from isIn() must not be negated");

        // A normal multi-character range is rendered as "start-end".
        assertEquals("a-e", range.toString(), "range should render as 'a-e'");
    }
}
