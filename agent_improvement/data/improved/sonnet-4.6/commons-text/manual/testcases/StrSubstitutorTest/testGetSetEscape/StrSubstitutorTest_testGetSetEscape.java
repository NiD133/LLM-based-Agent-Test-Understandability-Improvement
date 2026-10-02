package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetEscape {

    /**
     * Verifies that the escape character defaults to '$' and can be changed via setEscapeChar.
     * The escape character, when placed before a variable prefix (e.g. $${var}), prevents
     * substitution of that variable.
     */
    @Test
    void testGetSetEscape() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Default escape character is '$'
        assertEquals('$', sub.getEscapeChar());

        // Escape character can be changed to any character
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }
}
