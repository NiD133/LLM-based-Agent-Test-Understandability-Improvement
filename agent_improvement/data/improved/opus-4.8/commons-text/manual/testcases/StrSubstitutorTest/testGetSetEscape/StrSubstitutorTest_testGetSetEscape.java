package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the escape-character getter and setter of {@link StrSubstitutor}.
 */
public class StrSubstitutorTest_testGetSetEscape {

    @Test
    void testGetSetEscape() {
        final StrSubstitutor substitutor = new StrSubstitutor();

        // A freshly created substitutor uses the default escape character '$'.
        assertEquals('$', substitutor.getEscapeChar());

        // The escape character can be changed and is reflected by the getter.
        substitutor.setEscapeChar('<');
        assertEquals('<', substitutor.getEscapeChar());
    }
}
