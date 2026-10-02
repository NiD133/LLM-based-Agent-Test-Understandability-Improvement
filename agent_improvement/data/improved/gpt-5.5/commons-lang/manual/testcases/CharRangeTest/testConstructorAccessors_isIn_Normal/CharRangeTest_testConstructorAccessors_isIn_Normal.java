package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Normal extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Normal() {
        final char expectedStart = 'a';
        final char expectedEnd = 'e';
        final CharRange range = CharRange.isIn(expectedStart, expectedEnd);

        assertEquals(expectedStart, range.getStart());
        assertEquals(expectedEnd, range.getEnd());
        assertFalse(range.isNegated());
        assertEquals("a-e", range.toString());
    }
}
