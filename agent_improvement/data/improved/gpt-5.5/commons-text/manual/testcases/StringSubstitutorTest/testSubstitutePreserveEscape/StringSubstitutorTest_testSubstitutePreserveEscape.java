package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testSubstitutePreserveEscape {

    private static final String TEMPLATE_WITH_ESCAPED_AND_UNESCAPED_VARIABLES = "${not-escaped} $${escaped}";
    private static final String RESOLVED_WHEN_ESCAPES_ARE_REMOVED = "value ${escaped}";
    private static final String RESOLVED_WHEN_ESCAPES_ARE_PRESERVED = "value $${escaped}";

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @Test
    void testSubstitutePreserveEscape() throws IOException {
        final Map<String, String> valuesByVariableName = new HashMap<>();
        valuesByVariableName.put("not-escaped", "value");

        final StringSubstitutor sub = new StringSubstitutor(valuesByVariableName, "${", "}", '$');
        assertFalse(sub.isPreserveEscapes());
        assertEqualsCharSeq(RESOLVED_WHEN_ESCAPES_ARE_REMOVED,
                replace(sub, TEMPLATE_WITH_ESCAPED_AND_UNESCAPED_VARIABLES));

        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEqualsCharSeq(RESOLVED_WHEN_ESCAPES_ARE_PRESERVED,
                replace(sub, TEMPLATE_WITH_ESCAPED_AND_UNESCAPED_VARIABLES));
    }
}
