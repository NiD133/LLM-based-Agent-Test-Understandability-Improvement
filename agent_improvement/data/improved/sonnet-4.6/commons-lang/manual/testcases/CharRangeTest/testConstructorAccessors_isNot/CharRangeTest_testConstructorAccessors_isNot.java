package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that CharRange.isNot(char) constructs a negated single-character range
 * and that its accessor methods report the expected start, end, negation flag,
 * and string representation.
 *
 * <p>A "isNot" range covers every character in Unicode EXCEPT the given character.
 * Internally it is stored as a range from that character to itself (start == end)
 * with the negated flag set to true.  The toString() format for a negated
 * single-character range is "^" followed by the character, e.g. "^a".</p>
 */
public class CharRangeTest_testConstructorAccessors_isNot extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNot() {
        // Create a negated range that represents "every character except 'a'"
        final CharRange notA = CharRange.isNot('a');

        // A single-character isNot range has start == end == the excluded character
        assertEquals('a', notA.getStart(), "start should equal the excluded character");
        assertEquals('a', notA.getEnd(),   "end should equal the excluded character");

        // The range must be marked as negated (i.e. it excludes, rather than includes, 'a')
        assertTrue(notA.isNegated(), "isNot range must be negated");

        // The canonical string form of a negated single character is "^<char>"
        assertEquals("^a", notA.toString(), "toString of a negated single-char range should be '^a'");
    }
}
