package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Reproduces JIRA issue TEXT-178: interpolation of a template whose delimiters
 * are nested and overlapping in a "weird" way.
 *
 * <p>For the template {@code $${${a}}} the substitutor first resolves the inner
 * {@code ${a}} to {@code 1}, and the escaped {@code $$} together with the
 * surrounding braces leaves the literal text {@code ${1}}.</p>
 */
public class StringSubstitutorTest_testReplace_JiraText178_WeirdPatterns2 {

    /** Template using nested/escaped delimiters that exercise the boundary parsing. */
    private static final String TEMPLATE = "$${${a}}";

    /** Expected interpolation result: inner {@code ${a}} becomes {@code 1}. */
    private static final String EXPECTED = "${1}";

    /** Variable bindings shared by every assertion in a test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values (the template only resolves "a", the rest mirror the
        // original shared fixture so the substitutor sees the same bindings).
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Asserts that interpolating {@code template} yields {@code expected} for every
     * input type {@link StringSubstitutor} accepts, plus the in-place {@code replaceIn}
     * overloads. This mirrors the original test's exhaustive coverage of the public
     * {@code replace} / {@code replaceIn} surface.
     */
    private void assertReplacesAllInputTypes(final String expected, final String template) {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads: each accepts a different source type but returns a new String.
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expected, substitutor.replace(template.toCharArray()));
        assertEquals(expected, substitutor.replace(new StringBuffer(template)));
        assertEquals(expected, substitutor.replace(new StringBuilder(template)));
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)));
        // replace(Object) uses the argument's toString(), here the raw template.
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads: mutate the buffer in place and report whether
        // any substitution occurred (true here, since ${a} is resolved).
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expected, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expected, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expected, textBuilder.toString());
    }

    /**
     * Tests interpolation with weird boundary patterns (TEXT-178).
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns2() throws IOException {
        assertReplacesAllInputTypes(EXPECTED, TEMPLATE);
    }
}
