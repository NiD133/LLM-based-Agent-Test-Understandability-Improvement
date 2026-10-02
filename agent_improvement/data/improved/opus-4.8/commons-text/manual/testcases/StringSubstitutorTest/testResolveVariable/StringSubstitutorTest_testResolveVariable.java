package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#resolveVariable(String, TextStringBuilder, int, int)}
 * is a protected hook that subclasses can override to take full control of how a variable is
 * resolved, bypassing the configured value map.
 */
public class StringSubstitutorTest_testResolveVariable {

    /**
     * Asserts {@link CharSequence} equality, attaching the operands' lengths to the failure
     * message to make length mismatches easier to spot.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * An overridden {@code resolveVariable} should receive the exact variable name and buffer
     * coordinates, and its returned value should win over the substitutor's value map.
     */
    @Test
    void testResolveVariable() {
        final TextStringBuilder builder = new TextStringBuilder("Hi ${name}!");

        // The map says "name" -> "commons", but the override below ignores it and returns
        // "jakarta", proving resolveVariable takes precedence over the configured values.
        final Map<String, String> values = new HashMap<>();
        values.put("name", "commons");

        final StringSubstitutor substitutor = new StringSubstitutor(values) {

            @Override
            protected String resolveVariable(final String variableName, final TextStringBuilder buf,
                    final int startPos, final int endPos) {
                assertEquals("name", variableName);
                // The hook is handed the same buffer being substituted...
                assertSame(builder, buf);
                // ...and the bounds of the "${name}" expression within "Hi ${name}!":
                // index 3 is the leading '$', index 10 is just past the closing '}'.
                assertEquals(3, startPos);
                assertEquals(10, endPos);
                return "jakarta";
            }
        };

        substitutor.replaceIn(builder);

        assertEqualsCharSeq("Hi jakarta!", builder.toString());
    }
}
