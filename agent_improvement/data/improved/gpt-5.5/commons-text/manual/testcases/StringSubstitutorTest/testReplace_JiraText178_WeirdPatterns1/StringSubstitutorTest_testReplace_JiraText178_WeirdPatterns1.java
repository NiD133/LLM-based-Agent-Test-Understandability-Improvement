package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplace_JiraText178_WeirdPatterns1 {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String[] UNCHANGED_BOUNDARY_PATTERNS = {
        "$${",
        "$${a",
        "$$${",
        "$$${a",
        "$${${a",
        "${${a}",
        "${$${a}"
    };

    protected Map<String, String> values;

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));

        final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(replaceTemplate, builder.toString());
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns1() throws IOException {
        for (final String pattern : UNCHANGED_BOUNDARY_PATTERNS) {
            doNotReplace(pattern);
        }
    }
}
