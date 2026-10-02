package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testContainsNullArg extends AbstractLangTest {

    @Test
    void testContainsNullArg() {
        final CharRange singleCharacterRange = CharRange.is('a');

        final NullPointerException thrown = assertNullPointerException(() -> singleCharacterRange.contains(null));

        assertEquals("range", thrown.getMessage());
    }
}
