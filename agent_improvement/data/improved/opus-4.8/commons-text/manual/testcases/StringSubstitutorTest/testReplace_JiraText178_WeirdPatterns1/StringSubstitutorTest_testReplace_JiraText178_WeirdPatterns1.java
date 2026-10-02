package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Regression test for TEXT-178: certain malformed/boundary patterns such as
 * {@code "$${"} or {@code "${${a}"} must be left untouched by interpolation
 * rather than throwing or producing partial output.
 */
public class StringSubstitutorTest_testReplace_JiraText178_WeirdPatterns1 {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys (1-3 chars) that stress the parser's boundary handling.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // A couple of ordinary keys.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Asserts that {@code template} is returned verbatim by every form of
     * substitution, i.e. it contains no resolvable variable.
     */
    private void assertNotInterpolated(final String template) {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(String) returns the template unchanged.
        assertEquals(template, substitutor.replace(template));

        // replaceIn(...) reports "no change" and leaves the buffer unchanged.
        final TextStringBuilder builder = new TextStringBuilder(template);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(template, builder.toString());
    }

    /**
     * Tests interpolation with weird boundary patterns (TEXT-178).
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns1() throws IOException {
        assertNotInterpolated("$${");
        assertNotInterpolated("$${a");
        assertNotInterpolated("$$${");
        assertNotInterpolated("$$${a");
        assertNotInterpolated("$${${a");
        // "${a" is not a registered variable name, so it stays as-is.
        assertNotInterpolated("${${a}");
        assertNotInterpolated("${$${a}");
    }
}
