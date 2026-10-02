package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharRange#isNot(char)}, the factory for a negated single-character range.
 *
 * <p>A negated single-character range covers every character <em>except</em> the one given.
 * Even though it is negated, its start and end endpoints are still that single character,
 * and its string form is the character prefixed with {@code '^'}.</p>
 */
public class CharRangeTest_testConstructorAccessors_isNot extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNot() {
        // A negated range over the single character 'a' (i.e. "everything but 'a'").
        final CharRange negatedRangeForA = CharRange.isNot('a');

        // The endpoints still describe the single character 'a'.
        assertEquals('a', negatedRangeForA.getStart(), "start should be the single character 'a'");
        assertEquals('a', negatedRangeForA.getEnd(), "end should be the single character 'a'");

        // The range is negated, so isNegated() reports true.
        assertTrue(negatedRangeForA.isNegated(), "isNot(...) must produce a negated range");

        // Negation is rendered as a leading '^' in the string representation.
        assertEquals("^a", negatedRangeForA.toString(), "negated single-char range renders as \"^a\"");
    }
}
