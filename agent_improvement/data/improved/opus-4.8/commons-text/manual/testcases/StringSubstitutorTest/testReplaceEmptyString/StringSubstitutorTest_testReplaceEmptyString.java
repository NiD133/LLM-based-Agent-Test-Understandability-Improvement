package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} performs no substitution when given an
 * empty template, leaving the input unchanged.
 */
public class StringSubstitutorTest_testReplaceEmptyString {

    /** Variables available to the substitutor; none appear in an empty template. */
    private Map<String, String> buildValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }

    /**
     * An empty template has no {@code ${...}} expressions, so every form of
     * replacement must return the empty string and report that nothing changed.
     */
    @Test
    void testReplaceEmptyString() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(buildValues());
        final String emptyTemplate = StringUtils.EMPTY;

        // replace(String) returns the unchanged (empty) template
        assertEquals(emptyTemplate, substitutor.replace(emptyTemplate));

        // replaceIn(TextStringBuilder) reports no change and leaves the buffer empty
        final TextStringBuilder buffer = new TextStringBuilder(emptyTemplate);
        assertFalse(substitutor.replaceIn(buffer));
        assertEquals(emptyTemplate, buffer.toString());
    }
}
