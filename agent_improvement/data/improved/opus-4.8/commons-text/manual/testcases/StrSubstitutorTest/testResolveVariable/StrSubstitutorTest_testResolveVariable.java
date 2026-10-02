package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that a subclass can override the protected {@link StrSubstitutor#resolveVariable}
 * hook to control how each variable placeholder is resolved during substitution.
 */
public class StrSubstitutorTest_testResolveVariable {

    @Test
    void resolveVariableHookReceivesPlaceholderDetailsAndControlsResult() {
        // Template containing a single "${name}" placeholder, where the variable name
        // spans positions 3..10 within the text "Hi ${name}!".
        final StrBuilder template = new StrBuilder("Hi ${name}!");

        final Map<String, String> lookupValues = new HashMap<>();
        lookupValues.put("name", "commons");

        // Override resolveVariable to assert it is invoked with the expected arguments,
        // and to return a fixed replacement ("jakarta") instead of the mapped value.
        final StrSubstitutor substitutor = new StrSubstitutor(lookupValues) {

            @Override
            protected String resolveVariable(final String variableName, final StrBuilder buf,
                    final int startPos, final int endPos) {
                assertEquals("name", variableName);
                assertSame(template, buf);
                assertEquals(3, startPos);
                assertEquals(10, endPos);
                return "jakarta";
            }
        };

        substitutor.replaceIn(template);

        assertEquals("Hi jakarta!", template.toString());
    }
}
