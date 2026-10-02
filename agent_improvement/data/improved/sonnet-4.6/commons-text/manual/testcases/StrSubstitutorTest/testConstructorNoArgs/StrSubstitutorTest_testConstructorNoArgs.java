package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorNoArgs {

    /**
     * Verifies that the no-arg constructor leaves unresolvable variable references
     * unchanged because no value map is provided, so "${name}" is returned as-is.
     */
    @Test
    void testConstructorNoArgs() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("Hi ${name}", sub.replace("Hi ${name}"));
    }
}
