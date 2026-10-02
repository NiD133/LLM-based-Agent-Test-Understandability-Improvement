package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor}'s no-argument constructor.
 */
public class StrSubstitutorTest_testConstructorNoArgs {

    /**
     * A substitutor built with the no-arg constructor has no variable values,
     * so any {@code ${...}} placeholder is left untouched by {@code replace}.
     */
    @Test
    void testConstructorNoArgs() {
        final StrSubstitutor sub = new StrSubstitutor();

        final String unresolvedTemplate = "Hi ${name}";
        assertEquals(unresolvedTemplate, sub.replace(unresolvedTemplate));
    }
}
