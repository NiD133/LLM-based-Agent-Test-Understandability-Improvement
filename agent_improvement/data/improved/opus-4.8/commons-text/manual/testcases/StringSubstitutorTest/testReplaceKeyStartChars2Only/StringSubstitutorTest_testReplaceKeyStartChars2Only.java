package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a template consisting of only the first two characters of the
 * default variable-start sequence ("${") is left untouched, because it is not a
 * complete variable expression (it has no name and no closing brace).
 */
public class StringSubstitutorTest_testReplaceKeyStartChars2Only {

    /**
     * Builds a substitutor with a small set of arbitrary key/value pairs. None
     * of these keys can match the template under test; they only confirm that an
     * incomplete variable start is ignored even when lookups are available.
     */
    private StringSubstitutor newSubstitutor() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return new StringSubstitutor(values);
    }

    /**
     * Tests that an incomplete variable start ("${") is returned verbatim.
     */
    @Test
    void testReplaceKeyStartChars2Only() throws IOException {
        // "${" is the first two characters of the default "${...}" syntax.
        final String incompleteVarStart = StringSubstitutor.DEFAULT_VAR_START.substring(0, 2);

        final String actual = newSubstitutor().replace(incompleteVarStart);

        assertEquals(incompleteVarStart, actual);
    }
}
