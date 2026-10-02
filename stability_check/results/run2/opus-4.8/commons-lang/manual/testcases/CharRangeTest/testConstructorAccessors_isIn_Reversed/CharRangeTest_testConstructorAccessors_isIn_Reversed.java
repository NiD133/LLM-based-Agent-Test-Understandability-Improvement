package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    /**
     * When {@link CharRange#isIn(char, char)} receives its endpoints in reverse
     * order ('e' before 'a'), the range should normalize them so that the start
     * is the lower character and the end is the higher one. The resulting range
     * is a plain (non-negated) range whose text form is "a-e".
     */
    @Test
    void testConstructorAccessors_isIn_Reversed() {
        final CharRange reversedRange = CharRange.isIn('e', 'a');

        assertEquals('a', reversedRange.getStart(), "start should be the lower endpoint after normalization");
        assertEquals('e', reversedRange.getEnd(), "end should be the higher endpoint after normalization");
        assertFalse(reversedRange.isNegated(), "isIn should produce a non-negated range");
        assertEquals("a-e", reversedRange.toString(), "range should render as 'a-e'");
    }
}
