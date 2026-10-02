package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceNoVariables {

    private static final String TEMPLATE_WITHOUT_VARIABLES = "The balloon arrived.";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests replace with no variables.
     */
    @Test
    void testReplaceNoVariables() {
        assertNoReplacementOccurs(TEMPLATE_WITHOUT_VARIABLES);
    }

    private void assertNoReplacementOccurs(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        assertEquals(replaceTemplate, sub.replace(replaceTemplate));

        final StrBuilder builder = new StrBuilder(replaceTemplate);
        assertFalse(sub.replaceIn(builder));
        assertEquals(replaceTemplate, builder.toString());
    }
}
