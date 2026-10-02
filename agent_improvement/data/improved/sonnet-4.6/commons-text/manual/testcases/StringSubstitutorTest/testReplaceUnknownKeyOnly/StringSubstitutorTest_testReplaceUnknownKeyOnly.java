package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} leaves a variable expression unchanged when
 * the referenced key is not present in the value map.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnly {

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

    /**
     * Verifies that a template whose only variable reference uses a key absent from
     * the value map is returned verbatim — the unresolvable expression is preserved
     * rather than blanked or replaced with an empty string.
     */
    @Test
    void testReplaceUnknownKeyOnly() throws IOException {
        // "person" is not a key in the values map, so ${person} must survive unchanged.
        final String expected = "${person}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }
}
