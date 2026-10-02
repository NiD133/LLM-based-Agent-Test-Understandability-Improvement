package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        final CharRange reversedInputRange = CharRange.isIn('e', 'a');

        assertEquals('a', reversedInputRange.getStart());
        assertEquals('e', reversedInputRange.getEnd());
        assertFalse(reversedInputRange.isNegated());
        assertEquals("a-e", reversedInputRange.toString());
    }
}
