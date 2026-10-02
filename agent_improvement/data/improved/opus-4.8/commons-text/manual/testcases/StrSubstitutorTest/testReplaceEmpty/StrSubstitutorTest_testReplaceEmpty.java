package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that substituting into an empty template is a no-op:
 * an empty template has no variables to expand, so the result stays empty
 * and {@link StrSubstitutor#replaceIn} reports that nothing was changed.
 */
public class StrSubstitutorTest_testReplaceEmpty {

    /** Variable definitions available to the substitutor; never referenced by the empty template. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An empty template contains no variables, so replacement leaves it unchanged.
     */
    @Test
    void testReplaceEmpty() {
        final String emptyTemplate = "";
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(String) returns the template untouched
        assertEquals(emptyTemplate, sub.replace(emptyTemplate));

        // replaceIn(StrBuilder) makes no change, so it returns false and leaves the buffer empty
        final StrBuilder builder = new StrBuilder(emptyTemplate);
        assertFalse(sub.replaceIn(builder));
        assertEquals(emptyTemplate, builder.toString());
    }
}
