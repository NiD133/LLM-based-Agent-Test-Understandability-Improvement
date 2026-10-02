package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Same extends AbstractLangTest {

    /**
     * Verifies that CharRange.isIn(ch, ch) — where start and end are the same character —
     * produces a single-character, non-negated range whose string form is just that character
     * (no dash separator, no leading caret).
     */
    @Test
    @DisplayName("isIn with identical start/end creates a single-character non-negated range")
    void testConstructorAccessors_isIn_Same() {
        final CharRange rangea = CharRange.isIn('a', 'a');

        assertEquals('a', rangea.getStart(), "start character should be 'a'");
        assertEquals('a', rangea.getEnd(),   "end character should be 'a'");
        assertFalse(rangea.isNegated(),       "range should not be negated");
        assertEquals("a", rangea.toString(),  "single-character range 'a' renders as \"a\", with no dash or caret");
    }
}
