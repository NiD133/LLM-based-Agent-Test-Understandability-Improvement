package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testGetSetEscape {

    private static final char DEFAULT_ESCAPE = '$';
    private static final char CUSTOM_ESCAPE = '<';

    /**
     * Tests get set.
     */
    @Test
    void testGetSetEscape() {
        final StringSubstitutor sub = new StringSubstitutor();

        assertEquals(DEFAULT_ESCAPE, sub.getEscapeChar());
        sub.setEscapeChar(CUSTOM_ESCAPE);
        assertEquals(CUSTOM_ESCAPE, sub.getEscapeChar());
    }
}
