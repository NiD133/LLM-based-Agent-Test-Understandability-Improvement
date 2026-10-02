package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceWeirdPattens {

    private static final String[] WEIRD_BOUNDARY_PATTERNS = {
        "",
        "${}",
        "${ }",
        "${\t}",
        "${\n}",
        "${\b}",
        "${",
        "$}",
        "}",
        "${}$",
        "${${",
        "${${}}",
        "${$${}}",
        "${$$${}}",
        "${$$${$}}",
        "${${}}",
        "${${ }}"
    };

    private Map<String, String> values;

    private void assertNoReplacement(final String template) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        if (template == null) {
            assertNull(substitutor.replace((String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((StrBuilder) null));
            assertNull(substitutor.replace((StrBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((StrBuilder) null));
            assertFalse(substitutor.replaceIn((StrBuilder) null, 0, 100));
            return;
        }

        assertEquals(template, substitutor.replace(template));

        final StrBuilder builder = new StrBuilder(template);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(template, builder.toString());
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
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplaceWeirdPattens() {
        for (final String pattern : WEIRD_BOUNDARY_PATTERNS) {
            assertNoReplacement(pattern);
        }
    }
}
