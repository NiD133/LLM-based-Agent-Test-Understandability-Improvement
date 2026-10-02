package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorNoArgs {

    @Test
    void testConstructorNoArgsLeavesUnknownVariablesUnchanged() {
        final StrSubstitutor substitutor = new StrSubstitutor();

        assertEquals("Hi ${name}", substitutor.replace("Hi ${name}"));
    }
}
