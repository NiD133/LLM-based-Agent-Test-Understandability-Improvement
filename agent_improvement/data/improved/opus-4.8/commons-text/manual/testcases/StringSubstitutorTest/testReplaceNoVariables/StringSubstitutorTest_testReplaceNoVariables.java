package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} behaviour when the template contains no
 * {@code ${...}} variables: the text must be returned unchanged and no
 * in-place replacement must report a change.
 */
public class StringSubstitutorTest_testReplaceNoVariables {

    /**
     * A template that contains no {@code ${...}} variables, so none of the
     * configured values can ever match.
     */
    private static final String TEMPLATE_WITHOUT_VARIABLES = "The balloon arrived.";

    /** Variable values made available to the substitutor (none are referenced by the template). */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that a template with no variables is passed through untouched.
     */
    @Test
    void testReplaceNoVariables() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(String) returns the template unchanged.
        assertEquals(TEMPLATE_WITHOUT_VARIABLES, substitutor.replace(TEMPLATE_WITHOUT_VARIABLES));

        // replaceIn(...) reports "no change" and leaves the buffer unchanged.
        final TextStringBuilder builder = new TextStringBuilder(TEMPLATE_WITHOUT_VARIABLES);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(TEMPLATE_WITHOUT_VARIABLES, builder.toString());
    }
}
