package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StrSubstitutor returns templates unchanged when they contain no variable placeholders.
 */
public class StrSubstitutorTest_testReplaceNoVariables {

    private Map<String, String> values;

    /**
     * Asserts that the given template is returned as-is by all replace/replaceIn overloads,
     * because it contains no variable placeholders (e.g. no {@code ${...}} tokens).
     *
     * When {@code replaceTemplate} is {@code null}, asserts that every overload returns/yields
     * {@code null} or {@code false} rather than throwing.
     *
     * When {@code replaceTemplate} is a plain string without placeholders:
     *  - {@code replace(template)} must return the original string unchanged.
     *  - {@code replaceIn(builder)} must return {@code false} (no substitution performed)
     *    and leave the builder contents unchanged.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            // replace() should return the original string when there is nothing to substitute
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));

            // replaceIn() should report no change and leave the builder unmodified
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

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
     * A plain-text template with no variable markers should pass through the substitution
     * engine completely unchanged.
     */
    @Test
    void testReplaceNoVariables() {
        doTestNoReplace("The balloon arrived.");
    }
}
