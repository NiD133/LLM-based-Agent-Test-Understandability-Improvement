package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testResolveVariable {

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Verifies that StringSubstitutor passes the parsed variable name, source
     * buffer, and variable bounds to a protected resolveVariable override.
     */
    @Test
    void testResolveVariable() {
        final TextStringBuilder builder = new TextStringBuilder("Hi ${name}!");
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        final StringSubstitutor sub = new StringSubstitutor(map) {

            @Override
            protected String resolveVariable(final String variableName, final TextStringBuilder buf, final int startPos, final int endPos) {
                assertEquals("name", variableName);
                assertSame(builder, buf);
                assertEquals(3, startPos);
                assertEquals(10, endPos);
                return "jakarta";
            }
        };

        sub.replaceIn(builder);

        assertEqualsCharSeq("Hi jakarta!", builder.toString());
    }
}
