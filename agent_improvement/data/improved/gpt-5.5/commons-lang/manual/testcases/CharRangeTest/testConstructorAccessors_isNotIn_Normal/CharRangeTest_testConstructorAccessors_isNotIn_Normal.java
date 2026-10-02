package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Normal extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Normal() {
        final CharRange negatedRange = CharRange.isNotIn('a', 'e');

        assertEquals('a', negatedRange.getStart());
        assertEquals('e', negatedRange.getEnd());
        assertTrue(negatedRange.isNegated());
        assertEquals("^a-e", negatedRange.toString());
    }
}
