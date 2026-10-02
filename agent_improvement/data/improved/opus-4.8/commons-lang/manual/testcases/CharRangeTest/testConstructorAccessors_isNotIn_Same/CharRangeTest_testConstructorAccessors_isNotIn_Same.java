package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link CharRange#isNotIn(char, char)} when the start and end
 * characters are the same: it should produce a negated range covering that
 * single character.
 */
public class CharRangeTest_testConstructorAccessors_isNotIn_Same extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Same() {
        // A negated range whose start and end are both 'a'.
        final CharRange negatedSingleChar = CharRange.isNotIn('a', 'a');

        // Start and end collapse to the single character 'a'.
        assertEquals('a', negatedSingleChar.getStart());
        assertEquals('a', negatedSingleChar.getEnd());

        // The range is negated, so it represents "everything except 'a'".
        assertTrue(negatedSingleChar.isNegated());

        // A negated single-character range renders with a leading caret.
        assertEquals("^a", negatedSingleChar.toString());
    }
}
