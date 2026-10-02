package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the escape-character accessor and mutator on {@link StringSubstitutor}.
 */
public class StringSubstitutorTest_testGetSetEscape {

    /**
     * The escape character defaults to {@code '$'}, and {@link StringSubstitutor#setEscapeChar(char)}
     * updates the value returned by {@link StringSubstitutor#getEscapeChar()}.
     */
    @Test
    void testGetSetEscape() {
        final StringSubstitutor sub = new StringSubstitutor();

        assertEquals('$', sub.getEscapeChar(), "default escape character");

        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar(), "escape character after setEscapeChar");
    }
}
