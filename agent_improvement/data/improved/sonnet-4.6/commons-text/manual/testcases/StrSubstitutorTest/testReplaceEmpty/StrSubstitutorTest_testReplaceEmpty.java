package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceEmpty {

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
     * Tests that replacing variables in an empty string returns the empty string unchanged.
     */
    @Test
    void testReplaceEmpty() {
        final String emptyTemplate = "";
        final StrSubstitutor sub = new StrSubstitutor(values);

        // An empty template has no variables to replace, so the result is the same empty string.
        assertEquals(emptyTemplate, sub.replace(emptyTemplate));

        // replaceIn on an empty StrBuilder performs no substitutions and returns false.
        final StrBuilder bld = new StrBuilder(emptyTemplate);
        assertFalse(sub.replaceIn(bld));
        assertEquals(emptyTemplate, bld.toString());
    }
}
