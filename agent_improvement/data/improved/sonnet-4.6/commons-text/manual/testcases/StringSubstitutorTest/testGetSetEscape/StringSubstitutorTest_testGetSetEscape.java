package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testGetSetEscape {

    /**
     * Verifies that the default escape character is '$' and that it can be changed via setEscapeChar.
     */
    @Test
    void testGetSetEscape() {
        final StringSubstitutor sub = new StringSubstitutor();

        // Default escape char should be '$'
        assertEquals('$', sub.getEscapeChar());

        // After setting a new escape char, getEscapeChar should reflect the change
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }
}
