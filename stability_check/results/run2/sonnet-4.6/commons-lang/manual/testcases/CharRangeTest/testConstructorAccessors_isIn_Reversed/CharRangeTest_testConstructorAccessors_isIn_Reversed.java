package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    /**
     * When isIn() receives its arguments in reversed order (start > end), the range
     * is silently normalized so that start <= end. This test verifies that normalization
     * and that the range is not negated (isIn never produces a negated range).
     */
    @Test
    @DisplayName("isIn with reversed arguments normalizes start/end and is not negated")
    void testConstructorAccessors_isIn_Reversed() {
        // Supply end='e' before start='a' — the constructor must swap them internally.
        final CharRange reversedInputRange = CharRange.isIn('e', 'a');

        // After normalization the smaller character becomes the start.
        assertEquals('a', reversedInputRange.getStart(), "start should be the smaller char 'a'");
        // And the larger character becomes the end.
        assertEquals('e', reversedInputRange.getEnd(), "end should be the larger char 'e'");
        // isIn never negates; confirm the range covers exactly a-e, not its complement.
        assertFalse(reversedInputRange.isNegated(), "isIn range should not be negated");
        // The canonical string form reflects the normalized order.
        assertEquals("a-e", reversedInputRange.toString(), "string form should reflect normalized a-e range");
    }
}
