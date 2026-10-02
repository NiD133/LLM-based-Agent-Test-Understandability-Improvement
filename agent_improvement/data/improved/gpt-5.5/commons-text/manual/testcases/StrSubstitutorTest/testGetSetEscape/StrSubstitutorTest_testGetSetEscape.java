package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetEscape {

    private static final char DEFAULT_ESCAPE_CHARACTER = '$';
    private static final char CUSTOM_ESCAPE_CHARACTER = '<';

    /**
     * Tests get set.
     */
    @Test
    void testGetSetEscape() {
        final StrSubstitutor substitutor = new StrSubstitutor();

        assertEquals(DEFAULT_ESCAPE_CHARACTER, substitutor.getEscapeChar());
        substitutor.setEscapeChar(CUSTOM_ESCAPE_CHARACTER);
        assertEquals(CUSTOM_ESCAPE_CHARACTER, substitutor.getEscapeChar());
    }
}
