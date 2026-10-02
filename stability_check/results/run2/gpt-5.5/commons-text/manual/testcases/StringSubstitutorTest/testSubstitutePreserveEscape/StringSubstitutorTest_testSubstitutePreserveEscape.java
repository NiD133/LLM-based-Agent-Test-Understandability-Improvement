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

    private static final String VARIABLE_PREFIX = "${";
    private static final String VARIABLE_SUFFIX = "}";
    private static final char ESCAPE_CHARACTER = '$';

    private static final String RESOLVED_VARIABLE = "not-escaped";
    private static final String RESOLVED_VALUE = "value";
    private static final String ESCAPED_VARIABLE_REFERENCE = "$${escaped}";
    private static final String TEMPLATE = "${not-escaped} " + ESCAPED_VARIABLE_REFERENCE;

    private static final String DEFAULT_ESCAPES_REMOVED_RESULT = "value ${escaped}";
    private static final String PRESERVED_ESCAPES_RESULT = "value $${escaped}";

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
        final Map<String, String> values = new HashMap<>();
        values.put(RESOLVED_VARIABLE, RESOLVED_VALUE);

        final StringSubstitutor substitutor = new StringSubstitutor(values, VARIABLE_PREFIX, VARIABLE_SUFFIX, ESCAPE_CHARACTER);

        assertFalse(substitutor.isPreserveEscapes());
        assertEqualsCharSeq(DEFAULT_ESCAPES_REMOVED_RESULT, replace(substitutor, TEMPLATE));

        substitutor.setPreserveEscapes(true);

        assertTrue(substitutor.isPreserveEscapes());
        assertEqualsCharSeq(PRESERVED_ESCAPES_RESULT, replace(substitutor, TEMPLATE));
    }
}
